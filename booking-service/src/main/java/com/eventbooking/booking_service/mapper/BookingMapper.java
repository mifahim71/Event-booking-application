package com.eventbooking.booking_service.mapper;

import com.eventbooking.booking_service.dtos.BookingResponseDto;
import com.eventbooking.booking_service.entities.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingMapper {

    public BookingResponseDto bookingResponseDto(Booking booking, String message){

        BookingResponseDto responseDto = new BookingResponseDto();

        responseDto.setBookingId(booking.getId());
        responseDto.setStatus(booking.getStatus().toString());
        responseDto.setMessage(message);

        return responseDto;
    }
}
