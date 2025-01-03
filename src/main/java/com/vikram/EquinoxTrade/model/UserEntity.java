package com.vikram.EquinoxTrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vikram.EquinoxTrade.domain.UserRole;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * UserEntity
 */
@Entity
@Data
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fullname;
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // this makes password write only
  private String password;

  private String mobile;

  @Embedded
  private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

  private UserRole role = UserRole.ROLE_CUSTOMER;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public TwoFactorAuth getTwoFactorAuth() {
    return twoFactorAuth;
  }

  public void setTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
    this.twoFactorAuth = twoFactorAuth;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

}
