package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dtos.*;
import com.eventbooking.event_service.entities.Event;
import com.eventbooking.event_service.enums.Status;
import com.eventbooking.event_service.exception.EventNotFoundException;
import com.eventbooking.event_service.mapper.EventMapper;
import com.eventbooking.event_service.repository.EventRepository;
import com.eventbooking.event_service.repository.SeatRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final KafkaProducer kafkaProducer;

    private final EventMapper eventMapper;

    private final SeatRepository seatRepository;

    //create a new event
    public EventCreateResponseDto createEvent(@Valid EventCreateRequestDto requestDto, String organizerId) {

        Event event = Event.builder()
                .price(requestDto.getPrice())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .organizerId(Long.valueOf(organizerId))
                .totalSeats(requestDto.getTotalSeats())
                .location(requestDto.getLocation())
                .name(requestDto.getName())
                .status(Status.UPCOMING)
                .build();

        Event savedEvent = eventRepository.save(event);

        kafkaProducer.produceEvent(new CreateSeatEvent(event.getId(), event.getPrice(), event.getTotalSeats()));

        return eventMapper.eventCreateResponseDto(savedEvent);
    }

    //find events by its id
    public EventResponseDto getEventById(Long eventId) {

        Event event = findEventById(eventId);

        EventResponseDto responseDto = eventMapper.eventResponseDto(event);
        responseDto.setAvailableSeats(seatRepository.countByEventIdAndIsBooked(eventId, false));
        return responseDto;
    }

    //find event by city and date
    public Page<Event> getEvents(String city, LocalDate date, Pageable pageable) {

        return eventRepository.findByCityAndDate(city, date, pageable);
    }

    private Event findEventById(Long eventId){
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));
    }

}
