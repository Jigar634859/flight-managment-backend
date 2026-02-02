package com.example.flightapp.repository;

import com.example.flightapp.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Basic: exact (case-insensitive) match on from/to IATA codes
    List<Flight> findByFromCodeIgnoreCaseAndToCodeIgnoreCase(String from, String to);

    // Optional: filter by departure time window (for a specific date)
    List<Flight> findByFromCodeIgnoreCaseAndToCodeIgnoreCaseAndDepartAtBetween(
            String from, String to, LocalDateTime start, LocalDateTime end
    );
}