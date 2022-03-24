package com.gtech.payfast.Model.Ticket;

import com.gtech.payfast.Model.Config.Station;

import java.util.ArrayList;

public class Ticket {
    Boolean status;
    int type;
    String order_id;
    ArrayList<Station> stations;
    ArrayList<UpwardTicket> upwardTicket;

    public Ticket(Boolean status, int type, String order_id, ArrayList<Station> stations, ArrayList<UpwardTicket> upwardTicket) {
        this.status = status;
        this.type = type;
        this.order_id = order_id;
        this.stations = stations;
        this.upwardTicket = upwardTicket;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public ArrayList<UpwardTicket> getUpwardTicket() {
        return upwardTicket;
    }

    public void setUpwardTicket(ArrayList<UpwardTicket> upwardTicket) {
        this.upwardTicket = upwardTicket;
    }
}
