package com.gtech.payfast.Model.SVP;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.List;

public class SVStatus implements Serializable {

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

    public static class Data implements Serializable {
        private Integer supportType;

        private Integer operationTypeId;

        private Integer amount;

        private Integer masterExpiry;

        private Integer balanceTrip;

        private Integer registrationFee;

        private String masterTxnId;

        private Integer transactionId;

        private Integer balance;

        private Integer travelDate;

        private Integer graceExpiry;

        private List<? extends Trips> trips;

        private Integer qrType;

        private Integer tokenType;

        private Integer operatorId;

        private Integer timestamp;

        public Integer getSupportType() {
            return this.supportType;
        }

        public void setSupportType(Integer supportType) {
            this.supportType = supportType;
        }

        public Integer getOperationTypeId() {
            return this.operationTypeId;
        }

        public void setOperationTypeId(Integer operationTypeId) {
            this.operationTypeId = operationTypeId;
        }

        public Integer getAmount() {
            return this.amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getMasterExpiry() {
            return this.masterExpiry;
        }

        public void setMasterExpiry(Integer masterExpiry) {
            this.masterExpiry = masterExpiry;
        }

        public Integer getBalanceTrip() {
            return this.balanceTrip;
        }

        public void setBalanceTrip(Integer balanceTrip) {
            this.balanceTrip = balanceTrip;
        }

        public Integer getRegistrationFee() {
            return this.registrationFee;
        }

        public void setRegistrationFee(Integer registrationFee) {
            this.registrationFee = registrationFee;
        }

        public String getMasterTxnId() {
            return this.masterTxnId;
        }

        public void setMasterTxnId(String masterTxnId) {
            this.masterTxnId = masterTxnId;
        }

        public Integer getTransactionId() {
            return this.transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public Integer getBalance() {
            return this.balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getTravelDate() {
            return this.travelDate;
        }

        public void setTravelDate(Integer travelDate) {
            this.travelDate = travelDate;
        }

        public Integer getGraceExpiry() {
            return this.graceExpiry;
        }

        public void setGraceExpiry(Integer graceExpiry) {
            this.graceExpiry = graceExpiry;
        }

        public List<? extends Trips> getTrips() {
            return this.trips;
        }

        public void setTrips(List<? extends Trips> trips) {
            this.trips = trips;
        }

        public Integer getQrType() {
            return this.qrType;
        }

        public void setQrType(Integer qrType) {
            this.qrType = qrType;
        }

        public Integer getTokenType() {
            return this.tokenType;
        }

        public void setTokenType(Integer tokenType) {
            this.tokenType = tokenType;
        }

        public Integer getOperatorId() {
            return this.operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public Integer getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
        }

        public static class Trips implements Serializable {
            private String tokenStatus;

            private String qrCodeId;

            private String qrCodeData;

            private Integer issueTime;

            private Object extendedDestination;

            private Object scans;

            private String entryScanTime;

            private Integer expiryTime;

            private String type;

            private Integer transactionId;

            public String getTokenStatus() {
                return this.tokenStatus;
            }

            public void setTokenStatus(String tokenStatus) {
                this.tokenStatus = tokenStatus;
            }

            public String getQrCodeId() {
                return this.qrCodeId;
            }

            public void setQrCodeId(String qrCodeId) {
                this.qrCodeId = qrCodeId;
            }

            public String getQrCodeData() {
                return this.qrCodeData;
            }

            public void setQrCodeData(String qrCodeData) {
                this.qrCodeData = qrCodeData;
            }

            public Integer getIssueTime() {
                return this.issueTime;
            }

            public void setIssueTime(Integer issueTime) {
                this.issueTime = issueTime;
            }

            public Object getExtendedDestination() {
                return this.extendedDestination;
            }

            public void setExtendedDestination(Object extendedDestination) {
                this.extendedDestination = extendedDestination;
            }

            public Object getScans() {
                return this.scans;
            }

            public void setScans(Object scans) {
                this.scans = scans;
            }

            public String getEntryScanTime() {
                return this.entryScanTime;
            }

            public void setEntryScanTime(String entryScanTime) {
                this.entryScanTime = entryScanTime;
            }

            public Integer getExpiryTime() {
                return this.expiryTime;
            }

            public void setExpiryTime(Integer expiryTime) {
                this.expiryTime = expiryTime;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getTransactionId() {
                return this.transactionId;
            }

            public void setTransactionId(Integer transactionId) {
                this.transactionId = transactionId;
            }
        }
    }
}
