package com.gtech.payfast.Model.SVP;

import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.Config.Station;
import com.gtech.payfast.Model.Ticket.UpwardTicket;

import java.util.ArrayList;

public class SVDashboard {
    User user;
    UpwardTicket pass;
    UpwardTicket trip;
    ArrayList<Station> stations;
    boolean status;
    String error;

    public SVDashboard(User user, UpwardTicket pass, UpwardTicket trip, ArrayList<Station> stations) {
        this.user = user;
        this.pass = pass;
        this.trip = trip;
        this.stations = stations;
    }

    public SVDashboard(boolean status, String error) {
        this.status = status;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UpwardTicket getPass() {
        return pass;
    }

    public void setPass(UpwardTicket pass) {
        this.pass = pass;
    }

    public UpwardTicket getTrip() {
        return trip;
    }

    public void setTrip(UpwardTicket trip) {
        this.trip = trip;
    }

}
