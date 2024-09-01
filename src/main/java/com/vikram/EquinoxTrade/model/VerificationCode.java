package com.vikram.EquinoxTrade.model;

import com.vikram.EquinoxTrade.domain.VerificationType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * VerificationCode
 */
@Entity
public class VerificationCode {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String otp;

  @OneToOne(cascade = CascadeType.ALL)
  private UserEntity user;

  private String email;

  private String mobile;

  private VerificationType VerificationaType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public VerificationType getVerificationaType() {
    return VerificationaType;
  }

  public void setVerificationaType(VerificationType verificationaType) {
    VerificationaType = verificationaType;
  }

}
