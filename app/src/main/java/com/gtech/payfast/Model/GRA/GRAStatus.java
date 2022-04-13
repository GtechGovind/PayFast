package com.gtech.payfast.Model.GRA;

import java.io.Serializable;
import java.lang.Boolean;

public class GRAStatus implements Serializable {
  private Data data;

  private Boolean status;

  private String error;

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

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
