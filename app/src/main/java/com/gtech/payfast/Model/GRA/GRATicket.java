package com.gtech.payfast.Model.GRA;

import java.io.Serializable;

public class GRATicket implements Serializable {
    private Data data;

    private String pax_mobile;

    private String station_id;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getPax_mobile() {
        return pax_mobile;
    }

    public void setPax_mobile(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }
}
