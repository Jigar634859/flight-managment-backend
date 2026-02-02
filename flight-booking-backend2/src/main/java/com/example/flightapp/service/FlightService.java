
package com.example.flightapp.service;

import com.example.flightapp.entity.Flight;
import com.example.flightapp.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository repo;
    public FlightService(FlightRepository repo) { this.repo = repo; }

    public Flight addFlight(Flight flight) { return repo.save(flight); }
    public List<Flight> getAllFlights() { return repo.findAll(); }
    public Optional<Flight> getFlight(Long id) { return repo.findById(id); }

    public Optional<Flight> updateFlight(Long id, Flight updated) {
        return repo.findById(id).map(ex -> {
            if (updated.getCode() != null) ex.setCode(updated.getCode());
            if (updated.getAirline() != null) ex.setAirline(updated.getAirline());
            if (updated.getFrom() != null) ex.setFrom(updated.getFrom());
            if (updated.getTo() != null) ex.setTo(updated.getTo());
            if (updated.getDepartAt() != null) ex.setDepartAt(updated.getDepartAt());
            if (updated.getArriveAt() != null) ex.setArriveAt(updated.getArriveAt());
            if (updated.getPrice() != null) ex.setPrice(updated.getPrice());
            if (updated.getStatus() != null) ex.setStatus(updated.getStatus());
            return repo.save(ex);
        });
    }

    public boolean deleteFlight(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id); return true;
    }
    
    

public List<Flight> searchByFromTo(String from, String to) {
        return repo.findByFromCodeIgnoreCaseAndToCodeIgnoreCase(from, to);
    }


        public List<Flight> searchByFromToAndDate(String from, String to, LocalDate departDate) {
            // Build a day range: [00:00, 23:59:59.999]
            LocalDateTime start = departDate.atStartOfDay();
            LocalDateTime end = departDate.atTime(23, 59, 59, 999_000_000);
            return repo.findByFromCodeIgnoreCaseAndToCodeIgnoreCaseAndDepartAtBetween(from, to, start, end);
        }
    
}
