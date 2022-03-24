package com.gtech.payfast.Model.Ticket;

import java.util.ArrayList;

public class UpdateTicketDashboard {
    Boolean status;
    String user;
    ArrayList<UpwardTicket> upcomingOrders;
    ArrayList<UpwardTicket> recentOrders;

    public UpdateTicketDashboard(Boolean status, String user, ArrayList<UpwardTicket> upcomingOrders, ArrayList<UpwardTicket> recentOrders) {
        this.status = status;
        this.user = user;
        this.upcomingOrders = upcomingOrders;
        this.recentOrders = recentOrders;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<UpwardTicket> getUpcomingOrders() {
        return upcomingOrders;
    }

    public void setUpcomingOrders(ArrayList<UpwardTicket> upcomingOrders) {
        this.upcomingOrders = upcomingOrders;
    }

    public ArrayList<UpwardTicket> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(ArrayList<UpwardTicket> recentOrders) {
        this.recentOrders = recentOrders;
    }
}
