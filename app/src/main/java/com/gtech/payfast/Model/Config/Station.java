package com.gtech.payfast.Model.Config;

public class Station {

    String id, station_name, station_code, timestamp;

    public Station(String id, String station_name, String station_code, String timestamp) {
        this.id = id;
        this.station_name = station_name;
        this.station_code = station_code;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_code() {
        return station_code;
    }

    public void setStation_code(String station_code) {
        this.station_code = station_code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
