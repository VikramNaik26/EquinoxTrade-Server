package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.domain.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * Order
 */
@Entity
public class TradeOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private UserEntity user;

  @Column(nullable = false)
  private OrderType orderType;

  @Column(nullable = false)
  private BigDecimal price;

  private LocalDateTime timestamp = LocalDateTime.now();

  @Column(nullable = false)
  private OrderStatus status;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderItem orderItem;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

}
