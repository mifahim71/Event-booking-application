package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dtos.BookingEventDto;
import com.eventbooking.event_service.dtos.CheckSeatAvailable;
import com.eventbooking.event_service.dtos.SeatAvailableResponseDto;
import com.eventbooking.event_service.dtos.SeatResponseDto;
import com.eventbooking.event_service.entities.Seats;
import com.eventbooking.event_service.mapper.SeatMapper;
import com.eventbooking.event_service.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SeatMapper seatMapper;


    public List<SeatResponseDto> findSeats(CheckSeatAvailable checkSeatAvailable) {

        List<Seats> seats = seatRepository.findSeats(checkSeatAvailable.getEventId(), checkSeatAvailable.getSeats());

        return seats.stream().map(seatMapper::seatResponseDto).toList();
    }

    @Transactional
    public void updateIsBooked(BookingEventDto bookingEventDto) {

        seatRepository.markSeatsAsBooked(bookingEventDto.getEventId(), bookingEventDto.getSeats());
    }

    public List<SeatAvailableResponseDto> findSeatMap(Long eventId){


        String redisPrefix = "event:" + eventId + ":seat:";

        List<Seats> seats = seatRepository.findByEventId(eventId);

         return seats.stream().map(seat -> {

            SeatAvailableResponseDto responseDto = new SeatAvailableResponseDto();
            responseDto.setSeatNumber(seat.getSeatNumber());

            if(Boolean.TRUE.equals(redisTemplate.hasKey(redisPrefix+seat.getSeatNumber()))){

                responseDto.setBooked(false);
                responseDto.setLocked(true);
                return responseDto;
            }

            responseDto.setBooked(seat.isBooked());
            responseDto.setLocked(false);

            return responseDto;

        }).toList();



    }
}
