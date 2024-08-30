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

  @Embedded
  private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

  private UserRole role = UserRole.ROLE_CUSTOMER;

}
