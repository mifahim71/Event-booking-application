package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.dtos.BookingEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "booking-create-event";

    public void produceEvent(BookingEventDto eventDto){

        try {
            String eventJson = objectMapper.writeValueAsString(eventDto);
            kafkaTemplate.send(TOPIC, eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
