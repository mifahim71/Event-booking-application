package com.eventbooking.booking_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDto {

    private Long id;

    private Long eventId;

    private String seatNumber;

    private boolean isBooked;

    private double price;

}
