package com.gtech.payfast.Model.IssueToken.Response;

import java.util.List;

public class ResponseData {

    String masterTxnId, transactionId, operatorId, amount, registrationFee, balance, balanceTrip, supportType, qrType, tokenType, operationTypeId, timestamp, travelDate, masterExpiry, graceExpiry, order_no;
    List<Trips> trips;

    // MOBILE QR ORDER
    public ResponseData(String masterTxnId, String transactionId, String operatorId, String amount, String registrationFee, String balance, String balanceTrip, String supportType, String qrType, String tokenType, String operationTypeId, String timestamp, String travelDate, String masterExpiry, String graceExpiry, List<Trips> trips) {
        this.masterTxnId = masterTxnId;
        this.transactionId = transactionId;
        this.operatorId = operatorId;
        this.amount = amount;
        this.registrationFee = registrationFee;
        this.balance = balance;
        this.balanceTrip = balanceTrip;
        this.supportType = supportType;
        this.qrType = qrType;
        this.tokenType = tokenType;
        this.operationTypeId = operationTypeId;
        this.timestamp = timestamp;
        this.travelDate = travelDate;
        this.masterExpiry = masterExpiry;
        this.graceExpiry = graceExpiry;
        this.trips = trips;
    }

    // STORE VALUE PASS
    public ResponseData(String masterTxnId, String transactionId, String operatorId, String amount, String registrationFee, String balance, String balanceTrip, String supportType, String qrType, String tokenType, String operationTypeId, String timestamp, String travelDate, String masterExpiry, String graceExpiry, String order_no, List<Trips> trips) {
        this.masterTxnId = masterTxnId;
        this.transactionId = transactionId;
        this.operatorId = operatorId;
        this.amount = amount;
        this.registrationFee = registrationFee;
        this.balance = balance;
        this.balanceTrip = balanceTrip;
        this.supportType = supportType;
        this.qrType = qrType;
        this.tokenType = tokenType;
        this.operationTypeId = operationTypeId;
        this.timestamp = timestamp;
        this.travelDate = travelDate;
        this.masterExpiry = masterExpiry;
        this.graceExpiry = graceExpiry;
        this.order_no = order_no;
        this.trips = trips;
    }

    public String getMasterTxnId() {
        return masterTxnId;
    }
    public void setMasterTxnId(String masterTxnId) {
        this.masterTxnId = masterTxnId;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getRegistrationFee() {
        return registrationFee;
    }
    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }
    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public String getBalanceTrip() {
        return balanceTrip;
    }
    public void setBalanceTrip(String balanceTrip) {
        this.balanceTrip = balanceTrip;
    }
    public String getSupportType() {
        return supportType;
    }
    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }
    public String getQrType() {
        return qrType;
    }
    public void setQrType(String qrType) {
        this.qrType = qrType;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getOperationTypeId() {
        return operationTypeId;
    }
    public void setOperationTypeId(String operationTypeId) {
        this.operationTypeId = operationTypeId;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getTravelDate() {
        return travelDate;
    }
    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
    public String getMasterExpiry() {
        return masterExpiry;
    }
    public void setMasterExpiry(String masterExpiry) {
        this.masterExpiry = masterExpiry;
    }
    public String getGraceExpiry() {
        return graceExpiry;
    }
    public void setGraceExpiry(String graceExpiry) {
        this.graceExpiry = graceExpiry;
    }
    public List<Trips> getTrips() {
        return trips;
    }
    public void setTrips(List<Trips> trips) {
        this.trips = trips;
    }
    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
