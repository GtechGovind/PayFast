package com.gtech.payfast.Activity.SVP;

public class Order {
    String order_no, masterTxnId, pg_order_id, number, source, destination, type, count, fare, pg_id, operator, order_status, order_type, pass_type;

    public Order(String order_no, String masterTxnId, String pg_order_id, String number, String source, String destination, String type, String count, String fare, String pg_id, String operator, String order_status, String order_type, String pass_type) {
        this.order_no = order_no;
        this.masterTxnId = masterTxnId;
        this.pg_order_id = pg_order_id;
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.count = count;
        this.fare = fare;
        this.pg_id = pg_id;
        this.operator = operator;
        this.order_status = order_status;
        this.order_type = order_type;
        this.pass_type = pass_type;
    }

    // MOBILE QR ORDER
    public Order(String order_no, String number, String source, String destination, String type, String count, String fare, String pg_id, String operator, String order_status, String order_type) {
        this.order_no = order_no;
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.count = count;
        this.fare = fare;
        this.pg_id = pg_id;
        this.operator = operator;
        this.order_status = order_status;
        this.order_type = order_type;
    }

    // CHECK IF USER HAS PASS
    public Order(String number, String pass_type) {
        this.number = number;
        this.pass_type = pass_type;
    }

    // GET QRS
    public Order(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMasterTxnId() {
        return masterTxnId;
    }

    public void setMasterTxnId(String masterTxnId) {
        this.masterTxnId = masterTxnId;
    }

    public String getPg_order_id() {
        return pg_order_id;
    }

    public void setPg_order_id(String pg_order_id) {
        this.pg_order_id = pg_order_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getPg_id() {
        return pg_id;
    }

    public void setPg_id(String pg_id) {
        this.pg_id = pg_id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPass_type() {
        return pass_type;
    }

    public void setPass_type(String pass_type) {
        this.pass_type = pass_type;
    }
}
