package com.eventbooking.booking_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "event id is required")
    private Long eventId;

    @NotNull(message = "seatNumber is required")
    private List<String> seats;
}
