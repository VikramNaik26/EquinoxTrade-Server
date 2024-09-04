package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vikram.EquinoxTrade.domain.WithdrawalStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Withdrawal
 */
@Entity
public class Withdrawal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private WithdrawalStatus status;

  private BigDecimal amount;

  @ManyToOne
  private UserEntity user;

  private LocalDateTime dateTime = LocalDateTime.now();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public WithdrawalStatus getStatus() {
    return status;
  }

  public void setStatus(WithdrawalStatus status) {
    this.status = status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

}
