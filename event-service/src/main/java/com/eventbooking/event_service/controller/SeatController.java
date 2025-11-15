package com.eventbooking.event_service.controller;

import com.eventbooking.event_service.dtos.CheckSeatAvailable;
import com.eventbooking.event_service.dtos.SeatAvailableResponseDto;
import com.eventbooking.event_service.dtos.SeatResponseDto;
import com.eventbooking.event_service.entities.Seats;
import com.eventbooking.event_service.mapper.SeatMapper;
import com.eventbooking.event_service.repository.SeatRepository;
import com.eventbooking.event_service.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
@AllArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/available-seats")
    public ResponseEntity<List<SeatResponseDto>> hello(
            @RequestBody CheckSeatAvailable checkSeatAvailable
    ){

        List<SeatResponseDto> seatResponseDtos = seatService.findSeats(checkSeatAvailable);

        return ResponseEntity.ok().body(seatResponseDtos);
    }

    @GetMapping("/{eventId}/available-seats")
    public ResponseEntity<List<SeatAvailableResponseDto>> findAvailableSeats(
            @PathVariable Long eventId
    ){

        List<SeatAvailableResponseDto> seatMap = seatService.findSeatMap(eventId);
        return ResponseEntity.ok().body(seatMap);
    }
}
