package com.gtech.payfast.Model.Config;

import java.util.List;

public class StationsResponse {
    List<Station> stations;

    public StationsResponse(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
