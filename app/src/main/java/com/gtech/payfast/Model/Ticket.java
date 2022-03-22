package com.gtech.payfast.Model;

// Ticket creation
// source_id
// destination_id
// pass_id
// quantity
// fare
// pax_mobile
public class Ticket {

    String source_id, destination_id, pass_id, quantity, fare, pax_mobile;

    public Ticket(String source_id, String destination_id, String pass_id, String quantity, String fare, String pax_mobile) {
        this.source_id = source_id;
        this.destination_id = destination_id;
        this.pass_id = pass_id;
        this.quantity = quantity;
        this.fare = fare;
        this.pax_mobile = pax_mobile;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getPass_id() {
        return pass_id;
    }

    public void setPass_id(String pass_id) {
        this.pass_id = pass_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getPax_mobile() {
        return pax_mobile;
    }

    public void setPax_mobile(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }
}
