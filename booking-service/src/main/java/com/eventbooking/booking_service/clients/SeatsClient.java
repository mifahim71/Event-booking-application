package com.eventbooking.booking_service.clients;

import com.eventbooking.booking_service.dtos.BookSeatDto;
import com.eventbooking.booking_service.dtos.CheckSeatAvailable;
import com.eventbooking.booking_service.dtos.SeatResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "seats-service", url = "http://localhost:4002/seat")
public interface SeatsClient {

    @PostMapping("/available-seats")
    List<SeatResponseDto> getAvailableSeats(@RequestBody CheckSeatAvailable request);
}
