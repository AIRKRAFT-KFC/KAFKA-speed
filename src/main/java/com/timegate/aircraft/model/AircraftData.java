package com.timegate.aircraft.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AircraftData {
    @JsonProperty("hex")
    private String hex;
    
    @JsonProperty("reg_number")
    private String regNumber;
    
    @JsonProperty("flag")
    private String flag;
    
    @JsonProperty("lat")
    private double latitude;
    
    @JsonProperty("lng")
    private double longitude;
    
    @JsonProperty("alt")
    private double altitude;
    
    @JsonProperty("dir")
    private double direction;
    
    @JsonProperty("speed")
    private double speed;
    
    @JsonProperty("v_speed")
    private double verticalSpeed;
    
    @JsonProperty("flight_number")
    private String flightNumber;
    
    @JsonProperty("flight_icao")
    private String flightIcao;
    
    @JsonProperty("flight_iata")
    private String flightIata;
    
    @JsonProperty("dep_icao")
    private String departureIcao;
    
    @JsonProperty("dep_iata")
    private String departureIata;
    
    @JsonProperty("arr_icao")
    private String arrivalIcao;
    
    @JsonProperty("arr_iata")
    private String arrivalIata;
    
    @JsonProperty("airline_icao")
    private String airlineIcao;
    
    @JsonProperty("airline_iata")
    private String airlineIata;
    
    @JsonProperty("aircraft_icao")
    private String aircraftIcao;
    
    @JsonProperty("updated")
    private long updated;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("type")
    private String type;

    // Getters and Setters
    public String getHex() { return hex; }
    public void setHex(String hex) { this.hex = hex; }
    
    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
    
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    
    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }
    
    public double getDirection() { return direction; }
    public void setDirection(double direction) { this.direction = direction; }
    
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    
    public double getVerticalSpeed() { return verticalSpeed; }
    public void setVerticalSpeed(double verticalSpeed) { this.verticalSpeed = verticalSpeed; }
    
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    
    public String getFlightIcao() { return flightIcao; }
    public void setFlightIcao(String flightIcao) { this.flightIcao = flightIcao; }
    
    public String getFlightIata() { return flightIata; }
    public void setFlightIata(String flightIata) { this.flightIata = flightIata; }
    
    public String getDepartureIcao() { return departureIcao; }
    public void setDepartureIcao(String departureIcao) { this.departureIcao = departureIcao; }
    
    public String getDepartureIata() { return departureIata; }
    public void setDepartureIata(String departureIata) { this.departureIata = departureIata; }
    
    public String getArrivalIcao() { return arrivalIcao; }
    public void setArrivalIcao(String arrivalIcao) { this.arrivalIcao = arrivalIcao; }
    
    public String getArrivalIata() { return arrivalIata; }
    public void setArrivalIata(String arrivalIata) { this.arrivalIata = arrivalIata; }
    
    public String getAirlineIcao() { return airlineIcao; }
    public void setAirlineIcao(String airlineIcao) { this.airlineIcao = airlineIcao; }
    
    public String getAirlineIata() { return airlineIata; }
    public void setAirlineIata(String airlineIata) { this.airlineIata = airlineIata; }
    
    public String getAircraftIcao() { return aircraftIcao; }
    public void setAircraftIcao(String aircraftIcao) { this.aircraftIcao = aircraftIcao; }
    
    public long getUpdated() { return updated; }
    public void setUpdated(long updated) { this.updated = updated; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "AircraftData{" +
                "hex='" + hex + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", flag='" + flag + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", direction=" + direction +
                ", speed=" + speed +
                ", verticalSpeed=" + verticalSpeed +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightIcao='" + flightIcao + '\'' +
                ", flightIata='" + flightIata + '\'' +
                ", departureIcao='" + departureIcao + '\'' +
                ", departureIata='" + departureIata + '\'' +
                ", arrivalIcao='" + arrivalIcao + '\'' +
                ", arrivalIata='" + arrivalIata + '\'' +
                ", airlineIcao='" + airlineIcao + '\'' +
                ", airlineIata='" + airlineIata + '\'' +
                ", aircraftIcao='" + aircraftIcao + '\'' +
                ", updated=" + updated +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}