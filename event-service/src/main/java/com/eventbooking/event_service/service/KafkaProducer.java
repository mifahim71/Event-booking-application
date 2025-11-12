package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dtos.CreateSeatEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, CreateSeatEvent> kafkaTemplate;
    private static final String TOPIC = "create-seat-topic";


    public void produceEvent(CreateSeatEvent createSeatEvent){
        kafkaTemplate.send(TOPIC, createSeatEvent);
    }
}
