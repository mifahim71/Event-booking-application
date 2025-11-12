package com.eventbooking.event_service.service;

import com.eventbooking.event_service.dtos.CreateSeatEvent;
import com.eventbooking.event_service.entities.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SeatGeneratorConsumer {

    private final JdbcTemplate jdbcTemplate;

    @KafkaListener(topics = "create-seat-topic", groupId = "seat-generator-group")
    public void handleEventCreated(CreateSeatEvent event){
        log.info("Created seat for eventId: {}", event.getEventId());
        generateSeat(event);
    }

    private void generateSeat(CreateSeatEvent event) {

        List<Object[]> batchArgs = new ArrayList<>();


        for(int i=1; i<= event.getTotalSeats(); i++){
            String seatNumber = "A" + i;
            batchArgs.add(new Object[]{event.getEventId(), seatNumber, event.getPrice(), false});
        }

        jdbcTemplate.batchUpdate(
                "INSERT INTO seats(event_id, seat_number, price, is_booked) VALUES (?, ?, ?, ?)",
                batchArgs
        );
    }
}
