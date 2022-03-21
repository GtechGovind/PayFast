package com.gtech.payfast.Model.Config;

public class Fare {

    String id, fare_for, source, destination, fare;

    public Fare(String id, String fare_for, String source, String destination, String fare) {
        this.id = id;
        this.fare_for = fare_for;
        this.source = source;
        this.destination = destination;
        this.fare = fare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFare_for() {
        return fare_for;
    }

    public void setFare_for(String fare_for) {
        this.fare_for = fare_for;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
