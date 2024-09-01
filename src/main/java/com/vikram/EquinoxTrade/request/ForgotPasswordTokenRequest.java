package com.vikram.EquinoxTrade.request;

import com.vikram.EquinoxTrade.domain.VerificationType;

/**
 * ForgotPasswordRequestToken
 */
public class ForgotPasswordTokenRequest {

  private String sendTo;

  private VerificationType verificationType;

  public String getSendTo() {
    return sendTo;
  }

  public void setSendTo(String sendTo) {
    this.sendTo = sendTo;
  }

  public VerificationType getVerificationType() {
    return verificationType;
  }

  public void setVerificationType(VerificationType type) {
    this.verificationType = type;
  }

}
