package com.eventbooking.event_service.controller;

import com.eventbooking.event_service.dtos.EventCreateRequestDto;
import com.eventbooking.event_service.dtos.EventCreateResponseDto;
import com.eventbooking.event_service.dtos.EventResponseDto;
import com.eventbooking.event_service.entities.Event;
import com.eventbooking.event_service.exception.InvalidAccessForUserException;
import com.eventbooking.event_service.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    //checking if the controller is working fine
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(
            @RequestHeader("user-id") String organizerId,
            @RequestHeader("user-email") String email
    ){
        return ResponseEntity.ok().body("Working fine with organizer id: "+organizerId+" | email: "+email);
    }

    //create new events
    @PostMapping
    public ResponseEntity<EventCreateResponseDto> createEvent(
            @RequestHeader("user-id") String organizerId,
            @RequestHeader("user-role") String role,
            @Valid @RequestBody EventCreateRequestDto requestDto
    ){
        if(role.equals("USER")){
            throw new InvalidAccessForUserException("Access denied for role user");
        }
        EventCreateResponseDto responseDto = eventService.createEvent(requestDto, organizerId);
        return ResponseEntity.ok().body(responseDto);
    }

    //Find events by its id
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> getEventsById(
            @PathVariable Long eventId
    ){
        EventResponseDto responseDto = eventService.getEventById(eventId);
        return ResponseEntity.ok().body(responseDto);
    }

    //Find available events
    @GetMapping()
    public ResponseEntity<Page<Event>> getEvents(
            @RequestParam(name = "city", required = false, defaultValue = "Dhaka") String city,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "startTime") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
            ){

        if(page < 0 || size < 1){
            throw new IllegalArgumentException("Invalid Page Number or Page Size");
        }

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Event> events = eventService.getEvents(city, date, pageable);

        return ResponseEntity.ok().body(events);
    }
}
