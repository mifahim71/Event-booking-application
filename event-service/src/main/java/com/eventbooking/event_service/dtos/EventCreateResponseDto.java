package com.eventbooking.event_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateResponseDto {

    private Long id;

    private Long organizerId;

    private String name;
}
