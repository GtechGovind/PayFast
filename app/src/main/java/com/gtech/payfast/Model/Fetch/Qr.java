package com.gtech.payfast.Model.Fetch;

public class Qr {

    String order_no, masterTxnId, slave_qr_code, number, source, destination, type, qr_direction, qr_code_data, qr_status, slave_expiry_date;

    public Qr(String order_no, String masterTxnId, String slave_qr_code, String number, String source, String destination, String type, String qr_direction, String qr_code_data, String qr_status, String slave_expiry_date) {
        this.order_no = order_no;
        this.masterTxnId = masterTxnId;
        this.slave_qr_code = slave_qr_code;
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.qr_direction = qr_direction;
        this.qr_code_data = qr_code_data;
        this.qr_status = qr_status;
        this.slave_expiry_date = slave_expiry_date;
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

    public String getSlave_qr_code() {
        return slave_qr_code;
    }

    public void setSlave_qr_code(String slave_qr_code) {
        this.slave_qr_code = slave_qr_code;
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

    public String getQr_direction() {
        return qr_direction;
    }

    public void setQr_direction(String qr_direction) {
        this.qr_direction = qr_direction;
    }

    public String getQr_code_data() {
        return qr_code_data;
    }

    public void setQr_code_data(String qr_code_data) {
        this.qr_code_data = qr_code_data;
    }

    public String getQr_status() {
        return qr_status;
    }

    public void setQr_status(String qr_status) {
        this.qr_status = qr_status;
    }

    public String getSlave_expiry_date() {
        return slave_expiry_date;
    }

    public void setSlave_expiry_date(String slave_expiry_date) {
        this.slave_expiry_date = slave_expiry_date;
    }
}
