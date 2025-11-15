package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.clients.SeatsClient;
import com.eventbooking.booking_service.dtos.*;
import com.eventbooking.booking_service.entities.Booking;
import com.eventbooking.booking_service.enums.Status;
import com.eventbooking.booking_service.exceptions.SeatLimitExceededException;
import com.eventbooking.booking_service.mapper.BookingMapper;
import com.eventbooking.booking_service.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SeatsClient seatsClient;

    private final BookingMapper bookingMapper;

    private final KafkaProducer kafkaProducer;

    public BookingResponseDto createBooking(BookingRequestDto requestDto, Long userId) throws InterruptedException {

        String redisPrefix = "event:"+requestDto.getEventId()+":seat:";

        if(requestDto.getSeats().size() > 4){
            throw new SeatLimitExceededException("a user can only book 4 seats");
        }

        log.info("total seats from request: {}",requestDto.getSeats().size());
        for(String seat: requestDto.getSeats()){
            String redisKey = redisPrefix+seat;
            if(Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))){
                return new BookingResponseDto(null, Status.FAILED.toString(), "Booking failed some seat is already Locked", 0L);
            }
        }

        for(String seat: requestDto.getSeats()){
            String redisKey = redisPrefix+seat;
            redisTemplate.opsForValue().set(redisKey, "LOCKED", 60, TimeUnit.SECONDS);
        }

        List<SeatResponseDto> availableSeats = seatsClient.getAvailableSeats(new CheckSeatAvailable(requestDto.getEventId(), requestDto.getSeats()));
        log.info("total seats from seat service: {}", availableSeats.size());
        if(availableSeats.size() != requestDto.getSeats().size()){
            unlockSeats(redisPrefix, requestDto.getSeats());
            return new BookingResponseDto(null, Status.FAILED.toString(), "Booking failed some seat is already booked", 0L);
        }



        //That's just simple delay to test, if our lock is working, since we have not used any payment gateway in here
        Thread.sleep(10000);


        Booking booking = new Booking();
        booking.setBookedAt(LocalDateTime.now());
        booking.setStatus(Status.CONFIRMED);
        booking.setEventId(requestDto.getEventId());
        booking.setUserId(userId);
        booking.setTotalPrice(availableSeats.get(0).getPrice() * availableSeats.size());
        booking.setSeatNumbers(String.join(" , ", requestDto.getSeats()));

        Booking savedBooking = bookingRepository.save(booking);

        //send kafka producer to set booked to true in database
        kafkaProducer.produceEvent(new BookingEventDto(requestDto.getSeats(), requestDto.getEventId()));


        //unlock seats
        unlockSeats(redisPrefix, requestDto.getSeats());
        return bookingMapper.bookingResponseDto(savedBooking, "Booking successful of event with seats " + String.join(" , ", requestDto.getSeats()), savedBooking.getTotalPrice());
    }

    private void unlockSeats(String redisPrefix, List<String> seats){
        for (String seat: seats){
            redisTemplate.delete(redisPrefix+seat);
        }
    }
}
