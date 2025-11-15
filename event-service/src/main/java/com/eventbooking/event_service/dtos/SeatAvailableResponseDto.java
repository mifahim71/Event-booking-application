package com.eventbooking.event_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatAvailableResponseDto {

    private String seatNumber;

    private boolean booked;

    private boolean locked;
}
