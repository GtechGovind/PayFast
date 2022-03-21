package com.gtech.payfast.Model.IssueToken;

public class Data {

    String activationTime, destination, email, fare, mobile, name, operationTypeId, operatorId, operatorTransactionId, qrType, source, supportType, tokenType, trips, masterTxnId;

    // MOBILE QR
    public Data(String activationTime, String destination, String email, String fare, String mobile, String name, String operationTypeId, String operatorId, String operatorTransactionId, String qrType, String source, String supportType, String tokenType, String trips) {
        this.activationTime = activationTime;
        this.destination = destination;
        this.email = email;
        this.fare = fare;
        this.mobile = mobile;
        this.name = name;
        this.operationTypeId = operationTypeId;
        this.operatorId = operatorId;
        this.operatorTransactionId = operatorTransactionId;
        this.qrType = qrType;
        this.source = source;
        this.supportType = supportType;
        this.tokenType = tokenType;
        this.trips = trips;
    }

    // TRIP
    public Data(String activationTime, String destination, String email, String mobile, String name, String operationTypeId, String operatorId, String operatorTransactionId, String qrType, String source, String tokenType, String masterTxnId) {
        this.activationTime = activationTime;
        this.destination = destination;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.operationTypeId = operationTypeId;
        this.operatorId = operatorId;
        this.operatorTransactionId = operatorTransactionId;
        this.qrType = qrType;
        this.source = source;
        this.tokenType = tokenType;
        this.masterTxnId = masterTxnId;
    }


    // RELOAD
    public Data(String activationTime, String email, String fare, String mobile, String name, String operationTypeId, String operatorId, String operatorTransactionId, String tokenType, String trips, String masterTxnId) {
        this.activationTime = activationTime;
        this.email = email;
        this.fare = fare;
        this.mobile = mobile;
        this.name = name;
        this.operationTypeId = operationTypeId;
        this.operatorId = operatorId;
        this.operatorTransactionId = operatorTransactionId;
        this.tokenType = tokenType;
        this.trips = trips;
        this.masterTxnId = masterTxnId;
    }

    // GETTERS AND SETTERS
    public String getActivationTime() {
        return activationTime;
    }
    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFare() {
        return fare;
    }
    public void setFare(String fare) {
        this.fare = fare;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOperationTypeId() {
        return operationTypeId;
    }
    public void setOperationTypeId(String operationTypeId) {
        this.operationTypeId = operationTypeId;
    }
    public String getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    public String getOperatorTransactionId() {
        return operatorTransactionId;
    }
    public void setOperatorTransactionId(String operatorTransactionId) {
        this.operatorTransactionId = operatorTransactionId;
    }
    public String getQrType() {
        return qrType;
    }
    public void setQrType(String qrType) {
        this.qrType = qrType;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getSupportType() {
        return supportType;
    }
    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getTrips() {
        return trips;
    }
    public void setTrips(String trips) {
        this.trips = trips;
    }
    public String getMasterTxnId() {
        return masterTxnId;
    }
    public void setMasterTxnId(String masterTxnId) {
        this.masterTxnId = masterTxnId;
    }
}
