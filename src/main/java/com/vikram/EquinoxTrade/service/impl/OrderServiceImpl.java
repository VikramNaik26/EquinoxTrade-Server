package com.vikram.EquinoxTrade.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.OrderStatus;
import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.Order;
import com.vikram.EquinoxTrade.model.OrderItem;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.OrderItemRepository;
import com.vikram.EquinoxTrade.repository.OrderRepository;
import com.vikram.EquinoxTrade.service.OrderService;
import com.vikram.EquinoxTrade.service.WalletService;

import jakarta.transaction.Transactional;

/**
 * OrderServiceImpl
 */
@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private WalletService walletService;
  private OrderItemRepository orderItemRepository;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      WalletService walletService,
      OrderItemRepository orderItemRepository) {
    this.orderRepository = orderRepository;
    this.walletService = walletService;
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public Order createOrder(UserEntity user, OrderItem orderItem, OrderType orderType) {
    double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrderType(orderType);
    order.setPrice(BigDecimal.valueOf(price));
    order.setTimestamp(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    return orderRepository.save(order);
  }

  @Override
  public Order getOrderById(Long id) {
    return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
  }

  @Override
  public List<Order> getAllOrdersByUser(Long userId, OrderType orderType, String assetSymbol) {
    return orderRepository.findByUserId(userId);
  }

  private OrderItem createOrderItem(
      Coin coin,
      double quantity,
      BigDecimal buyPrice,
      BigDecimal sellPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);

    return orderItemRepository.save(orderItem);
  }

  @Transactional
  private Order byAsset(Coin coin, double quantity, UserEntity user) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }

    double buyPrice = coin.getCurrentPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, BigDecimal.valueOf(buyPrice), BigDecimal.ZERO);

    Order order = createOrder(user, orderItem, OrderType.BUY);
    orderItem.setOrder(order);

    walletService.payOrderPayment(order, user);

    order.setStatus(OrderStatus.SUCCESS);
    order.setOrderType(OrderType.BUY);

    Order saveOrder = orderRepository.save(order);
    // TODO: create asset

    return saveOrder;
  }

  @Transactional
  private Order sellAsset(Coin coin, double quantity, UserEntity user) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }

    double sellPrice = coin.getCurrentPrice();

    double buyPrice = assetToSell.getPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, BigDecimal.valueOf(buyPrice), BigDecimal.valueOf(sellPrice));

    Order order = createOrder(user, orderItem, OrderType.SELL);
    orderItem.setOrder(order);

    if (assetToSell.getQuantity() >= quantity) {
      order.setStatus(OrderStatus.SUCCESS);
      order.setOrderType(OrderType.SELL);

      Order saveOrder = orderRepository.save(order);
      // TODO: create asset

      walletService.payOrderPayment(order, user);

      Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
      if (updatedAsset.getQauntity() * coin.getCurrentPrice() <= 1) {
        assetService.deleteAsset(updatedAsset.getId());
      }

      return saveOrder;
    }

    throw new RuntimeException("Not enough assets to sell");
  }

  @Override
  @Transactional
  public Order processOrder(Coin coin, double quantity, OrderType orderType, UserEntity user) {
    if (orderType.equals(OrderType.BUY)) {
      return byAsset(coin, quantity, user);
    } else if (orderType.equals(OrderType.SELL)) {
      return sellAsset(coin, quantity, user);
    }

    throw new IllegalArgumentException("Invalid order type");
  }

}
