package com.gtech.payfast.Model.TP;

public class CreateTP {
    int source_id, destination_id, fare;
    String pax_mobile;

    public CreateTP(int source_id, int destination_id, int fare, String pax_mobile) {
        this.source_id = source_id;
        this.destination_id = destination_id;
        this.fare = fare;
        this.pax_mobile = pax_mobile;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getPax_mobile() {
        return pax_mobile;
    }

    public void setPax_mobile(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }
}
