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
}
