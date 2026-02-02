package com.example.flightapp.controller;

import com.example.flightapp.dto.BookingResponse;
import com.example.flightapp.dto.FlightSummary;
import com.example.flightapp.dto.BookingUpdateRequest; // <-- only if you add editing
import com.example.flightapp.entity.Booking;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.service.BookingService;
import com.example.flightapp.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final FlightService flightService;

    public BookingController(BookingService bookingService, FlightService flightService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
    }

    // ---------- Create booking (authenticated user) ----------
    @PostMapping
    public Booking create(@RequestBody Booking req, Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Long userId = Long.valueOf(auth.getName()); // subject in JWT is userId
        req.setUserId(userId);
        return bookingService.create(req);
    }

    // ---------- Get my bookings (enriched with flight status) ----------
    @GetMapping("/my-bookings")
    public List<BookingResponse> myBookings(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Long userId = Long.valueOf(auth.getName());
        return bookingService.myBookings(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ---------- Get one booking (owner only, enriched with flight status) ----------
    @GetMapping("/{id}")
    public BookingResponse one(@PathVariable Long id, Authentication auth) {
        Booking b = bookingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Long userId = Long.valueOf(auth.getName());
        if (!userId.equals(b.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        return toResponse(b);
    }

    // ---------- (Optional) Edit booking (owner only) ----------
    // Requires you to add BookingUpdateRequest DTO and BookingService.updateForUser(...)
    @PatchMapping("/{id}")
    public BookingResponse update(@PathVariable Long id,
                                  @Validated @RequestBody BookingUpdateRequest req,
                                  Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Long userId = Long.valueOf(auth.getName());
        Booking updated = bookingService.updateForUser(id, userId, req, flightId -> flightService.getFlight(flightId));
        return toResponse(updated);
    }

    // ---------- Mapper: entity -> response (adds live flightStatus + summary) ----------
    private BookingResponse toResponse(Booking b) {
        Flight f = flightService.getFlight(b.getFlightId()).orElse(null);

        String currentFlightStatus = (f != null ? f.getStatus() : null);
        FlightSummary fs = (f == null) ? null :
                new FlightSummary(
                        f.getId(), f.getCode(), f.getAirline(), f.getFrom(), f.getTo(),
                        f.getDepartAt(), f.getArriveAt()
                );

        return new BookingResponse(
                b.getId(), b.getFlightId(), b.getPassengerName(), b.getPassengerEmail(), b.getPassengerPhone(),
                b.getNumberOfPassengers(), b.getSeatPreference(), b.getTotalPrice(),
                b.getBookingDate(), b.getStatus(),
                currentFlightStatus, fs
        );
    }
}