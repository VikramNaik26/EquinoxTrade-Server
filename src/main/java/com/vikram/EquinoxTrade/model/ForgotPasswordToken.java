package com.vikram.EquinoxTrade.model;

import com.vikram.EquinoxTrade.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * ForgotPassword
 */
@Entity
public class ForgotPasswordToken {

  @Id
  private String id;

  @OneToOne
  private UserEntity user;

  private String otp;

  private VerificationType verificationType;

  private String sendTo;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public VerificationType getVerificationType() {
    return verificationType;
  }

  public void setVerificationType(VerificationType verificationType) {
    this.verificationType = verificationType;
  }

  public String getSendTo() {
    return sendTo;
  }

  public void setSendTo(String sendTo) {
    this.sendTo = sendTo;
  }
}