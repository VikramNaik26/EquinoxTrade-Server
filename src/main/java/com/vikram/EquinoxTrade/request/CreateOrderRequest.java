package com.vikram.EquinoxTrade.request;

import com.vikram.EquinoxTrade.domain.OrderType;

/**
 * CreateOrderRequest
 */
public class CreateOrderRequest {

  private String coinId;
  private OrderType orderType;
  private Double quantity;

  public String getCoinId() {
    return coinId;
  }

  public void setCoinId(String coinId) {
    this.coinId = coinId;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

}
