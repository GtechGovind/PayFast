package com.gtech.payfast.Model.Auth;

public class User {

    private String pax_name;
    private String insert_date;
    private String pax_email;
    private String pax_mobile;
    private Object is_verified;
    private Integer pax_id;
    private Integer points;

    public User(String pax_name, String insert_date, String pax_email, String pax_mobile, Object is_verified, Integer pax_id, Integer points) {
        this.pax_name = pax_name;
        this.insert_date = insert_date;
        this.pax_email = pax_email;
        this.pax_mobile = pax_mobile;
        this.is_verified = is_verified;
        this.pax_id = pax_id;
        this.points = points;
    }

    public User(String pax_name, String pax_email, String pax_mobile) {
        this.pax_name = pax_name;
        this.pax_email = pax_email;
        this.pax_mobile = pax_mobile;
    }

    public User(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }

    public String getPax_name() {
        return pax_name;
    }

    public void setPax_name(String pax_name) {
        this.pax_name = pax_name;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public String getPax_email() {
        return pax_email;
    }

    public void setPax_email(String pax_email) {
        this.pax_email = pax_email;
    }

    public String getPax_mobile() {
        return pax_mobile;
    }

    public void setPax_mobile(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }

    public Object getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Object is_verified) {
        this.is_verified = is_verified;
    }

    public Integer getPax_id() {
        return pax_id;
    }

    public void setPax_id(Integer pax_id) {
        this.pax_id = pax_id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
