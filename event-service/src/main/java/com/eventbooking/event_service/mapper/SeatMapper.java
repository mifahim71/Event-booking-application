package com.eventbooking.event_service.mapper;

import com.eventbooking.event_service.dtos.SeatResponseDto;
import com.eventbooking.event_service.entities.Seats;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatMapper {

    public SeatResponseDto seatResponseDto(Seats seats){

        SeatResponseDto responseDto = new SeatResponseDto();

        responseDto.setSeatNumber(seats.getSeatNumber());
        responseDto.setPrice(seats.getPrice());
        responseDto.setBooked(seats.isBooked());
        responseDto.setEventId(seats.getEvent().getId());
        responseDto.setId(seats.getId());

        return responseDto;
    }
}
