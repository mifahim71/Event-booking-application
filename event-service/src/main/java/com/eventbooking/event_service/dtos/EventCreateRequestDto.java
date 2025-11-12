package com.eventbooking.event_service.dtos;

import com.eventbooking.event_service.entities.Seats;
import com.eventbooking.event_service.enums.Status;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateRequestDto {

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "startTime is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "total seat number is required")
    @Min(value=50, message = "Seat Number should be greater then 50")
    @Max(value=200, message = "Seat number should be less then 200")
    private int totalSeats;
}
