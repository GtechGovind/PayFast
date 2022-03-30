package com.gtech.payfast.Model.TP;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class ReloadTP implements Serializable {
  private String order_id;

  private Integer reloadAmount;

  public ReloadTP(String order_id, Integer reloadAmount) {
    this.order_id = order_id;
    this.reloadAmount = reloadAmount;
  }

  public String getOrder_id() {
    return this.order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public Integer getReloadAmount() {
    return this.reloadAmount;
  }

  public void setReloadAmount(Integer reloadAmount) {
    this.reloadAmount = reloadAmount;
  }
}
