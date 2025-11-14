package com.eventbooking.event_service.mapper;

import com.eventbooking.event_service.dtos.EventCreateResponseDto;
import com.eventbooking.event_service.dtos.EventResponseDto;
import com.eventbooking.event_service.entities.Event;
import com.eventbooking.event_service.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventMapper {

    public EventCreateResponseDto eventCreateResponseDto(Event event){

        EventCreateResponseDto responseDto = new EventCreateResponseDto();
        responseDto.setId(event.getId());
        responseDto.setName(event.getName());
        responseDto.setOrganizerId(event.getOrganizerId());

        return responseDto;
    }


    public EventResponseDto eventResponseDto(Event event){

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setName(event.getName());
        responseDto.setLocation(event.getLocation());
        responseDto.setStartTime(event.getStartTime());
        responseDto.setEndTime(event.getEndTime());
        responseDto.setPrice(event.getPrice());
        responseDto.setStatus(event.getStatus().toString());

        return responseDto;
    }
}
