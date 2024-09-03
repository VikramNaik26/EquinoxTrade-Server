package com.vikram.EquinoxTrade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.TradeOrder;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.request.CreateOrderRequest;
import com.vikram.EquinoxTrade.service.CoinService;
import com.vikram.EquinoxTrade.service.OrderService;
import com.vikram.EquinoxTrade.service.UserService;

/**
 * OrderController
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

  private OrderService orderService;
  private UserService userService;
  private CoinService coinService;
  // private WalletTransactionService walletTransactionService;

  public OrderController(
      OrderService orderService,
      UserService userService,
      CoinService coinService
  // WalletTransactionService walletTransactionService
  ) {
    this.orderService = orderService;
    this.userService = userService;
    this.coinService = coinService;
    // this.walletTransactionService = walletTransactionService;
  }

  @PostMapping("/pay")
  public ResponseEntity<TradeOrder> payOrderPayment(
      @RequestHeader("Authorization") String jwt,
      @RequestBody CreateOrderRequest request) {
    UserEntity user = userService.findUserByJwt(jwt);
    Coin coin = coinService.findById(request.getCoinId());

    TradeOrder order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(), user);

    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping("/{orderId}")
  public ResponseEntity<TradeOrder> getOrderById(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId) {
    UserEntity user = userService.findUserByJwt(jwt);

    TradeOrder order = orderService.getOrderById(orderId);

    if (order.getUser().getId().equals(user.getId())) {
      return new ResponseEntity<>(order, HttpStatus.OK);
    }

    throw new IllegalArgumentException("You are not authorized to view this order");
  }

  @GetMapping
  public ResponseEntity<List<TradeOrder>> getAllOrdersForUser(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(required = false) OrderType order_type,
      @RequestParam(required = false) String asset_symbol) {
    System.out.println("JWT" + jwt);
    Long userId = userService.findUserByJwt(jwt).getId();
    System.out.println("userId" + userId);

    return new ResponseEntity<>(orderService.getAllOrdersByUser(userId, order_type, asset_symbol), HttpStatus.OK);
  }

}
