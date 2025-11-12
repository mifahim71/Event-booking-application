package com.eventbooking.event_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {

    private String name;

    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double price;

    private int totalSeats;

    private String status;

}
