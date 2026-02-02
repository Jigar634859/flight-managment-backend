
package com.example.flightapp.controller;

import com.example.flightapp.entity.Flight;
import com.example.flightapp.service.FlightService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService service;
    public FlightController(FlightService service) { this.service = service; }

    @PostMapping
    public Flight create(@RequestBody Flight flight) {
        return service.addFlight(flight);
    }

    @GetMapping
    public List<Flight> all() { return service.getAllFlights(); }

    @GetMapping("/{id}")
    public Flight one(@PathVariable Long id) {
        return service.getFlight(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }
    

@GetMapping("/search")
    public List<Flight> search(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date == null) {
            return service.searchByFromTo(from, to);
        }
        return service.searchByFromToAndDate(from, to, date);
    }


    @PutMapping("/{id}")
    public Flight update(@PathVariable Long id, @RequestBody Flight f) {
        return service.updateFlight(id, f).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (!service.deleteFlight(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
    }
}
