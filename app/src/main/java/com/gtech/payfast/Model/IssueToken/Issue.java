package com.gtech.payfast.Model.IssueToken;

public class Issue {

    Data data;
    Payment payment;

    public Issue(Data data, Payment payment) {
        this.data = data;
        this.payment = payment;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
