package com.example.flightapp.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private Long flightId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private Integer numberOfPassengers;
    private String seatPreference;
    private Integer totalPrice;
    private LocalDateTime bookingDate;
    private String status;           // Booking status (e.g., Confirmed)
    private String flightStatus;     // ⬅ Derived from Flight
    private FlightSummary flight;    // ⬅ Optional: embed useful flight fields

    public BookingResponse(Long id, Long flightId, String passengerName, String passengerEmail, String passengerPhone,
                           Integer numberOfPassengers, String seatPreference, Integer totalPrice,
                           LocalDateTime bookingDate, String status,
                           String flightStatus, FlightSummary flight) {
        this.id = id; this.flightId = flightId; this.passengerName = passengerName; this.passengerEmail = passengerEmail;
        this.passengerPhone = passengerPhone; this.numberOfPassengers = numberOfPassengers; this.seatPreference = seatPreference;
        this.totalPrice = totalPrice; this.bookingDate = bookingDate; this.status = status;
        this.flightStatus = flightStatus; this.flight = flight;
    }
    // getters only
    public Long getId() { return id; }
    public Long getFlightId() { return flightId; }
    public String getPassengerName() { return passengerName; }
    public String getPassengerEmail() { return passengerEmail; }
    public String getPassengerPhone() { return passengerPhone; }
    public Integer getNumberOfPassengers() { return numberOfPassengers; }
    public String getSeatPreference() { return seatPreference; }
    public Integer getTotalPrice() { return totalPrice; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public String getFlightStatus() { return flightStatus; }
    public FlightSummary getFlight() { return flight; }
}