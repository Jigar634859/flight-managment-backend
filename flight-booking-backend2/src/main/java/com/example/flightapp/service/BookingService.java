package com.example.flightapp.service;

import com.example.flightapp.dto.BookingUpdateRequest;
import com.example.flightapp.entity.Booking;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BookingService {

    private final BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
    }

    /** Create a new booking (userId must be set by the controller). */
    @Transactional
    public Booking create(Booking b) {
        return repo.save(b);
    }

    /** Return bookings for the authenticated user. */
    @Transactional(readOnly = true)
    public List<Booking> myBookings(Long userId) {
        return repo.findByUserId(userId);
    }

    /** Find booking by id. */
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        return repo.findById(id);
    }

    /**
     * Update an existing booking for the owning user.
     * Rules:
     *  - Only the owner can edit.
     *  - Cannot edit after flight departure.
     *  - Cannot edit if booking status is "Cancelled".
     *  - Recalculate totalPrice = flight.price * numberOfPassengers when changed.
     *
     * @param bookingId    the booking to update
     * @param userId       the caller's user id (from JWT subject)
     * @param req          patch body with editable fields
     * @param flightLoader function that loads Flight by id (supplied by controller to avoid coupling)
     */
    @Transactional
    public Booking updateForUser(Long bookingId,
                                 Long userId,
                                 BookingUpdateRequest req,
                                 Function<Long, Optional<Flight>> flightLoader) {

        Booking b = repo.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if (!userId.equals(b.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        // Load current flight to enforce time rule & price calculation
        Flight f = flightLoader.apply(b.getFlightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

        // Disallow edits after departure
        if (f.getDepartAt() != null && f.getDepartAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot edit after departure");
        }
        // Disallow edits for cancelled bookings
        if ("Cancelled".equalsIgnoreCase(b.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot edit a cancelled booking");
        }

        // Apply allowed fields (basic patch)
        if (req.getPassengerName() != null)  b.setPassengerName(req.getPassengerName());
        if (req.getPassengerEmail() != null) b.setPassengerEmail(req.getPassengerEmail());
        if (req.getPassengerPhone() != null) b.setPassengerPhone(req.getPassengerPhone());
        if (req.getSeatPreference() != null) b.setSeatPreference(req.getSeatPreference());

        if (req.getNumberOfPassengers() != null && req.getNumberOfPassengers() > 0) {
            b.setNumberOfPassengers(req.getNumberOfPassengers());
            // Recalculate total if you price per seat
            if (f.getPrice() != null) {
                b.setTotalPrice(f.getPrice() * req.getNumberOfPassengers());
            }
        }

        return repo.save(b);
    }
}