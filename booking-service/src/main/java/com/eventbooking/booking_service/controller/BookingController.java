package com.eventbooking.booking_service.controller;

import com.eventbooking.booking_service.dtos.BookingRequestDto;
import com.eventbooking.booking_service.dtos.BookingResponseDto;
import com.eventbooking.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book-seat")
    public ResponseEntity<BookingResponseDto> bookSeats(
            @Valid @RequestBody BookingRequestDto requestDto,
            @RequestHeader("user-id") Long userId
            ) throws InterruptedException {

        BookingResponseDto responseDto = bookingService.createBooking(requestDto, userId);
        return ResponseEntity.ok().body(responseDto);
    }
}
