package com.vikram.EquinoxTrade.service;

import java.util.List;

import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.TradeOrder;
import com.vikram.EquinoxTrade.model.OrderItem;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * OrderService
 */
public interface OrderService {

  public TradeOrder createOrder(UserEntity user, OrderItem orderItem, OrderType orderType);

  public TradeOrder getOrderById(Long id);

  public List<TradeOrder> getAllOrdersByUser(Long userId, OrderType type, String assetSymbol);

  public TradeOrder processOrder(Coin coin, double quantity, OrderType orderType, UserEntity user);

}
