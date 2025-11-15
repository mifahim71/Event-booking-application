package com.eventbooking.booking_service.entities;

import com.eventbooking.booking_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id not found")
    private Long userId;

    @NotNull(message = "Event id not found")
    private Long eventId;

    @NotNull(message = "seat id not found")
    private String seatNumbers;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "total price not found")
    private double totalPrice;

    @NotNull(message = "bookedAt not found")
    private LocalDateTime bookedAt;
}
