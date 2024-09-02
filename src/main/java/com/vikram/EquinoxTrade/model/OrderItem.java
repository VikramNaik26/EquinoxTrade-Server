package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * OrderItem
 */
@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double quantity;

  @ManyToOne
  private Coin coin;

  private BigDecimal buyPrice;

  private BigDecimal sellPrice;

  @JsonIgnore
  @OneToOne
  private Order order;

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

  public Coin getCoin() {
    return coin;
  }

  public void setCoin(Coin coin) {
    this.coin = coin;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(BigDecimal buyPrice) {
    this.buyPrice = buyPrice;
  }

  public BigDecimal getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(BigDecimal sellPrice) {
    this.sellPrice = sellPrice;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

}
