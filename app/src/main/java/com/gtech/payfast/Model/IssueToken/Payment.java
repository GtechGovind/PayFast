package com.gtech.payfast.Model.IssueToken;

public class Payment {

    String pass_price, pgId;

    public Payment(String pass_price, String pgId) {
        this.pass_price = pass_price;
        this.pgId = pgId;
    }

    public String getPass_price() {
        return pass_price;
    }

    public void setPass_price(String pass_price) {
        this.pass_price = pass_price;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }
}
