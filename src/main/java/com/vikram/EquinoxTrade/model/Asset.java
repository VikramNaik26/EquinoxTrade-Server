package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Asset
 */
@Entity
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double quantity;
  private BigDecimal buyPrice;

  @ManyToOne
  private Coin coin;

  @ManyToOne
  private UserEntity user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(BigDecimal buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Coin getCoin() {
    return coin;
  }

  public void setCoin(Coin coin) {
    this.coin = coin;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
