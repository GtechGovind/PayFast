package com.gtech.payfast.Model.GRA;

import java.io.Serializable;

public class PenaltyInfo implements Serializable {
    private Data data;

    private Boolean status;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
