package com.gtech.payfast.Model.Config;

public class Fare {
    Boolean status;
    Integer fare;

    public Fare(Boolean status, Integer fare) {
        this.status = status;
        this.fare = fare;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }
}
