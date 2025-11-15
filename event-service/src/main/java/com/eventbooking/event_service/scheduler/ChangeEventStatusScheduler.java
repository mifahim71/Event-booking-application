package com.eventbooking.event_service.scheduler;

import com.eventbooking.event_service.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChangeEventStatusScheduler {

    private EventRepository eventRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateEventStatus(){
        eventRepository.markEventsAsStarted(LocalDateTime.now());
    }

}
