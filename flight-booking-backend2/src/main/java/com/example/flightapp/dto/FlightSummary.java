package com.example.flightapp.dto;

import java.time.LocalDateTime;

public class FlightSummary {
    private Long id;
    private String code;
    private String airline;
    private String from;
    private String to;
    private LocalDateTime departAt;
    private LocalDateTime arriveAt;

    public FlightSummary(Long id, String code, String airline, String from, String to,
                         LocalDateTime departAt, LocalDateTime arriveAt) {
        this.id = id; this.code = code; this.airline = airline; this.from = from; this.to = to;
        this.departAt = departAt; this.arriveAt = arriveAt;
    }
    // getters
    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getAirline() { return airline; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public LocalDateTime getDepartAt() { return departAt; }
    public LocalDateTime getArriveAt() { return arriveAt; }
}