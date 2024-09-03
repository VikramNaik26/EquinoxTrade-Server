package com.vikram.EquinoxTrade.service;

import java.util.List;

import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.Order;
import com.vikram.EquinoxTrade.model.OrderItem;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * OrderService
 */
public interface OrderService {

  public Order createOrder(UserEntity user, OrderItem orderItem, OrderType orderType);

  public Order getOrderById(Long id);

  public List<Order> getAllOrdersByUser(Long userId, OrderType type, String assetSymbol);

  public Order processOrder(Coin coin, double quantity, OrderType orderType, UserEntity user);

}
