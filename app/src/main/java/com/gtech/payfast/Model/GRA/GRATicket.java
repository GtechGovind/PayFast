package com.gtech.payfast.Model.GRA;

import java.io.Serializable;
import java.lang.Boolean;

public class GRATicket implements Serializable {
  private PenaltyInfo penaltyInfo;

  public PenaltyInfo getPenaltyInfo() {
    return this.penaltyInfo;
  }

  public void setPenaltyInfo(PenaltyInfo penaltyInfo) {
    this.penaltyInfo = penaltyInfo;
  }

  public static class PenaltyInfo implements Serializable {
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

  }
}
