package com.gtech.payfast.Model;

import com.gtech.payfast.Model.Config.Station;

import java.util.ArrayList;

public class TicketViewResponse {
    Boolean status;
    int type;
    String order_id;
    ArrayList<Station> stations;
    ArrayList<UpwardTicket> upwardTickets;

    public TicketViewResponse(Boolean status, int type, String order_id) {
        this.status = status;
        this.type = type;
        this.order_id = order_id;
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

    public ArrayList<UpwardTicket> getUpwardTickets() {
        return upwardTickets;
    }

    public void setUpwardTickets(ArrayList<UpwardTicket> upwardTickets) {
        this.upwardTickets = upwardTickets;
    }
}
