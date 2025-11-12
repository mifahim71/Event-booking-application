package com.eventbooking.event_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSeatEvent {

    private Long eventId;

    private Double price;

    private int totalSeats;

}
