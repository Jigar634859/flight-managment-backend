
package com.example.flightapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String airline;
    @Column(name = "origin_iata")
    private String fromCode;
    @Column(name = "destination_iata")
    private String toCode;
    private LocalDateTime departAt;
    private LocalDateTime arriveAt;
    private Integer price;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
    public String getFrom() { return fromCode; }
    public void setFrom(String from) { this.fromCode = from; }
    public String getTo() { return toCode; }
    public void setTo(String to) { this.toCode = to; }
    public LocalDateTime getDepartAt() { return departAt; }
    public void setDepartAt(LocalDateTime departAt) { this.departAt = departAt; }
    public LocalDateTime getArriveAt() { return arriveAt; }
    public void setArriveAt(LocalDateTime arriveAt) { this.arriveAt = arriveAt; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
