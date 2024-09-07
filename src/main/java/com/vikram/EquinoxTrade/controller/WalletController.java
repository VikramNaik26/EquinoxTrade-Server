package com.vikram.EquinoxTrade.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.vikram.EquinoxTrade.model.PaymentOrder;
import com.vikram.EquinoxTrade.model.TradeOrder;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Wallet;
import com.vikram.EquinoxTrade.model.WalletTransaction;
import com.vikram.EquinoxTrade.service.OrderService;
import com.vikram.EquinoxTrade.service.PaymentService;
import com.vikram.EquinoxTrade.service.UserService;
import com.vikram.EquinoxTrade.service.WalletService;

/**
 * WalletController
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

  private WalletService walletService;
  private UserService userService;
  private OrderService orderService;
  private PaymentService paymentService;

  public WalletController(
      WalletService walletService,
      UserService userService,
      OrderService orderService,
      PaymentService paymentService) {
    this.walletService = walletService;
    this.userService = userService;
    this.orderService = orderService;
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);

    return new ResponseEntity<>(walletService.getUserWallet(user), HttpStatus.OK);
  }

  @PutMapping("/{walletId}/transfer")
  public ResponseEntity<Wallet> walletToWalletTransfer(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long walletId,
      @RequestBody WalletTransaction walletTransaction) {
    UserEntity senderUser = userService.findUserByJwt(jwt);
    Wallet reciecerWallet = walletService.findWalletById(walletId);

    Wallet wallet = walletService.walletToWalletTransfer(senderUser, reciecerWallet, walletTransaction.getAmount());

    return new ResponseEntity<>(wallet, HttpStatus.OK);
  }

  @PutMapping("/order/{orderId}/pay")
  public ResponseEntity<Wallet> payOrderPayment(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId,
      @RequestBody WalletTransaction walletTransaction) {
    UserEntity user = userService.findUserByJwt(jwt);

    TradeOrder order = orderService.getOrderById(orderId);

    Wallet wallet = walletService.payOrderPayment(order, user);

    return new ResponseEntity<>(wallet, HttpStatus.OK);
  }

  @PutMapping("/order/deposit")
  public ResponseEntity<Wallet> addMoneyToWallet(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(name = "order_id") Long orderId,
      @RequestParam(name = "payment_id") String paymentId) throws RazorpayException {
    UserEntity user = userService.findUserByJwt(jwt);

    Wallet wallet = walletService.getUserWallet(user);

    PaymentOrder order = paymentService.getPaymentOrderById(orderId);

    Boolean status = paymentService.proceedPaymentOrder(order, paymentId);

    if(wallet.getBalance() == null) {
      wallet.setBalance(BigDecimal.ZERO);
    }

    if (status) {
      wallet = walletService.addBalance(wallet, order.getAmount());
    }

    return new ResponseEntity<>(wallet, HttpStatus.OK);
  }

}
