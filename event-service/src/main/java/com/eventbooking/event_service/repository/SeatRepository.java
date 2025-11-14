package com.eventbooking.event_service.repository;

import com.eventbooking.event_service.entities.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Long> {

    int countByEventIdAndIsBooked(Long eventId, Boolean isBooked);

    @Query("SELECT s FROM Seats s WHERE s.event.id = :eventId AND s.seatNumber IN :seatNames AND isBooked = false")
    List<Seats> findSeats(@Param("eventId") Long eventId, @Param("seatNames") List<String> seatNames);


    @Modifying
    @Query("UPDATE Seats s SET s.isBooked = true WHERE s.event.id = :eventId AND s.seatNumber IN :seats AND s.isBooked = false")
    int markSeatsAsBooked(@Param("eventId") Long eventId, @Param("seats") List<String> seats);
}
