package com.gtech.payfast.Model.GRA;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.List;

public class Data implements Serializable {

  private Integer supportType;

  private Integer operationTypeId;

  private String entryScanStation;

  private Integer amount;

  private Integer balanceTrip;

  private List<? extends Penalties> penalties;

  private Object registrationFee;

  private Object entryScanTime;

  private List<? extends Penalties> overTravelCharges;

  private String masterTxnId;

  private Integer originalTokenType;

  private Integer transactionId;

  private String refTxnId;

  private Integer balance;

  private Integer travelDate;

  private Integer qrType;

  private Object exitScanExpiryTime;

  private String expiry;

  private Integer tokenType;

  private Integer operatorId;

  private String exitUptoStation;

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

  public String getEntryScanStation() {
    return this.entryScanStation;
  }

  public void setEntryScanStation(String entryScanStation) {
    this.entryScanStation = entryScanStation;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Integer getBalanceTrip() {
    return this.balanceTrip;
  }

  public void setBalanceTrip(Integer balanceTrip) {
    this.balanceTrip = balanceTrip;
  }

  public List<? extends Penalties> getPenalties() {
    return this.penalties;
  }

  public void setPenalties(List<? extends Penalties> penalties) {
    this.penalties = penalties;
  }

  public Object getRegistrationFee() {
    return this.registrationFee;
  }

  public void setRegistrationFee(Object registrationFee) {
    this.registrationFee = registrationFee;
  }

  public Object getEntryScanTime() {
    return this.entryScanTime;
  }

  public void setEntryScanTime(Object entryScanTime) {
    this.entryScanTime = entryScanTime;
  }

  public List<? extends Penalties> getOverTravelCharges() {
    return this.overTravelCharges;
  }

  public void setOverTravelCharges(List<? extends Penalties> overTravelCharges) {
    this.overTravelCharges = overTravelCharges;
  }

  public String getMasterTxnId() {
    return this.masterTxnId;
  }

  public void setMasterTxnId(String masterTxnId) {
    this.masterTxnId = masterTxnId;
  }

  public Integer getOriginalTokenType() {
    return this.originalTokenType;
  }

  public void setOriginalTokenType(Integer originalTokenType) {
    this.originalTokenType = originalTokenType;
  }

  public Integer getTransactionId() {
    return this.transactionId;
  }

  public void setTransactionId(Integer transactionId) {
    this.transactionId = transactionId;
  }

  public String getRefTxnId() {
    return this.refTxnId;
  }

  public void setRefTxnId(String refTxnId) {
    this.refTxnId = refTxnId;
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

  public Integer getQrType() {
    return this.qrType;
  }

  public void setQrType(Integer qrType) {
    this.qrType = qrType;
  }

  public Object getExitScanExpiryTime() {
    return this.exitScanExpiryTime;
  }

  public void setExitScanExpiryTime(Object exitScanExpiryTime) {
    this.exitScanExpiryTime = exitScanExpiryTime;
  }

  public String getExpiry() {
    return this.expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
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

  public String getExitUptoStation() {
    return this.exitUptoStation;
  }

  public void setExitUptoStation(String exitUptoStation) {
    this.exitUptoStation = exitUptoStation;
  }

  public Integer getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  public static class Penalties implements Serializable {
    private Integer operationTypeId;

    private Integer amount;

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
  }
}
