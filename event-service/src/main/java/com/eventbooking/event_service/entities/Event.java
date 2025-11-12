package com.eventbooking.event_service.entities;

import com.eventbooking.event_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "startTime is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull(message = "Organizer id is required")
    private Long organizerId;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "total seat number is required")
    @Min(value=50, message = "Seat Number should be greater then 50")
    @Max(value=200, message = "Seat number should be less then 200")
    private int totalSeats;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Event status is required")
    private Status status;

}
