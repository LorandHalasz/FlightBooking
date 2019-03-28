package entity;

import java.sql.Date;

public class Flight {
    private Integer idFlight;
    private String origin;
    private String destination;
    private Integer flightTime;
    private String company;
    private Double price;
    private Date departure;
    private Integer freeSeats;

    public Flight(){

    }

    public Flight(Integer idFlight, String origin, String destination, Integer flightTime, String company, Double price, Date departure, Integer freeSeats) {
        this.idFlight = idFlight;
        this.origin = origin;
        this.destination = destination;
        this.flightTime = flightTime;
        this.company = company;
        this.price = price;
        this.departure = departure;
        this.freeSeats = freeSeats;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Integer flightTime) {
        this.flightTime = flightTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }
}
