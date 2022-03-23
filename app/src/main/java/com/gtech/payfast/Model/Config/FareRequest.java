package com.gtech.payfast.Model.Config;

public class FareRequest {
     Integer source, destination, pass_id;

    public FareRequest(Integer source, Integer destination, Integer pass_id) {
        this.source = source;
        this.destination = destination;
        this.pass_id = pass_id;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getPass_id() {
        return pass_id;
    }

    public void setPass_id(Integer pass_id) {
        this.pass_id = pass_id;
    }
}
