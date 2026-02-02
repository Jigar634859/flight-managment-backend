package com.example.flightapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BookingUpdateRequest {
    @NotBlank
    private String passengerName;
    @NotBlank
    private String passengerEmail;
    @NotBlank
    private String passengerPhone;
    @NotBlank
    private String seatPreference;

    @Min(1)
    private Integer numberOfPassengers;

    // getters/setters
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }
    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }
    public String getSeatPreference() { return seatPreference; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }
    public Integer getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(Integer numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }
}