package com.gtech.payfast.Model.Pass;

import com.gtech.payfast.Model.IssueToken.Data;

public class Trip {

    Data data;

    public Trip(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
