package com.vikram.EquinoxTrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * TwoFactorOTP
 */
@Entity
@Data
public class TwoFactorOTP {

  @Id
  private String id;

  private String otp;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToOne
  private UserEntity user;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String jwt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

}
