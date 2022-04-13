package com.gtech.payfast.Model;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public class RefundDetail implements Serializable {
  private Boolean status;

  private Refund refund;

  private String error;

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Refund getRefund() {
    return this.refund;
  }

  public void setRefund(Refund refund) {
    this.refund = refund;
  }

  public static class Refund implements Serializable {
    private Integer pass_price;

    private Integer processing_fee;

    private Integer refund_amount;

    private String order_id;

    private Integer processing_fee_amount;

    public Integer getPass_price() {
      return this.pass_price;
    }

    public void setPass_price(Integer pass_price) {
      this.pass_price = pass_price;
    }

    public Integer getProcessing_fee() {
      return this.processing_fee;
    }

    public void setProcessing_fee(Integer processing_fee) {
      this.processing_fee = processing_fee;
    }

    public Integer getRefund_amount() {
      return this.refund_amount;
    }

    public void setRefund_amount(Integer refund_amount) {
      this.refund_amount = refund_amount;
    }

    public String getOrder_id() {
      return this.order_id;
    }

    public void setOrder_id(String order_id) {
      this.order_id = order_id;
    }

    public Integer getProcessing_fee_amount() {
      return this.processing_fee_amount;
    }

    public void setProcessing_fee_amount(Integer processing_fee_amount) {
      this.processing_fee_amount = processing_fee_amount;
    }
  }
}
