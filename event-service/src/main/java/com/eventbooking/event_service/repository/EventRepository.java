package com.eventbooking.event_service.repository;

import com.eventbooking.event_service.entities.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    @Query("""
    SELECT e\s
    FROM Event e\s
    WHERE (:city IS NULL OR LOWER(e.location) LIKE LOWER(CONCAT('%', :city, '%')))
    AND (:date IS NULL OR DATE(e.startTime) = :date)
   \s""")
    Page<Event> findByCityAndDate(
            @Param("city") String city,
            @Param("date") LocalDate date,
            Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.status = 'ONGOING' WHERE e.status = 'UPCOMING' AND e.startTime <= :now")
    int markEventsAsStarted(@Param("now") LocalDateTime now);
    
}
