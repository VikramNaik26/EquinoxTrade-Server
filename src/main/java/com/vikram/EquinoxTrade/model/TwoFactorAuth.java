package com.vikram.EquinoxTrade.model;

import com.vikram.EquinoxTrade.domain.VerificationaType;

import lombok.Data;

@Data
public class TwoFactorAuth {

  private boolean isEnabled = false;
  private VerificationaType sendTo;

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public VerificationaType getSendTo() {
    return sendTo;
  }

  public void setSendTo(VerificationaType sendTo) {
    this.sendTo = sendTo;
  }

}
