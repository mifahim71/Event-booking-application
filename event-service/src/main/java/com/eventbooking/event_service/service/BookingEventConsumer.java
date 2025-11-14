package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dtos.BookingEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingEventConsumer {

    private final ObjectMapper objectMapper;

    private final SeatService seatService;

    @KafkaListener(topics = "booking-create-event", groupId = "booking-confirm-group")
    public void bookingEventConsumer(String eventJson){

        try {
            BookingEventDto bookingEventDto = objectMapper.readValue(eventJson, BookingEventDto.class);
            seatService.updateIsBooked(bookingEventDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
