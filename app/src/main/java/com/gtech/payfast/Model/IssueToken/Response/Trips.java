package com.gtech.payfast.Model.IssueToken.Response;

public class Trips {

    String qrCodeData, expiryTime, entryScanTime, issueTime, tokenStatus, qrCodeId, transactionId, type, extendedDestination, scans;

    public Trips(String qrCodeData, String expiryTime, String entryScanTime, String issueTime, String tokenStatus, String qrCodeId, String transactionId, String type, String extendedDestination, String scans) {
        this.qrCodeData = qrCodeData;
        this.expiryTime = expiryTime;
        this.entryScanTime = entryScanTime;
        this.issueTime = issueTime;
        this.tokenStatus = tokenStatus;
        this.qrCodeId = qrCodeId;
        this.transactionId = transactionId;
        this.type = type;
        this.extendedDestination = extendedDestination;
        this.scans = scans;
    }

    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getEntryScanTime() {
        return entryScanTime;
    }

    public void setEntryScanTime(String entryScanTime) {
        this.entryScanTime = entryScanTime;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtendedDestination() {
        return extendedDestination;
    }

    public void setExtendedDestination(String extendedDestination) {
        this.extendedDestination = extendedDestination;
    }

    public String getScans() {
        return scans;
    }

    public void setScans(String scans) {
        this.scans = scans;
    }
}
