package com.gtech.payfast.Model.Config;

public class Station {

    String id;
    String stn_name;
    String stn_code;
    String insert_date;

    public Station(String id, String stn_name, String stn_code, String insert_date) {
        this.id = id;
        this.stn_name = stn_name;
        this.stn_code = stn_code;
        this.insert_date = insert_date;
    }

    public Station(String stn_id, String stn_name) {
        this.id = stn_id;
        this.stn_name = stn_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStn_name() {
        return stn_name;
    }

    public void setStn_name(String stn_name) {
        this.stn_name = stn_name;
    }

    public String getStn_code() {
        return stn_code;
    }

    public void setStn_code(String stn_code) {
        this.stn_code = stn_code;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }


}
