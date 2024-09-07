package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;

import com.vikram.EquinoxTrade.domain.PaymentMethod;
import com.vikram.EquinoxTrade.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * PaymentOrder
 */
@Entity
public class PaymentOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private BigDecimal amount;

  private PaymentOrderStatus status;

  private PaymentMethod paymentMethod;

  @ManyToOne
  private UserEntity user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public PaymentOrderStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentOrderStatus status) {
    this.status = status;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

}