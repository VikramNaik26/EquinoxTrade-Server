package com.vikram.EquinoxTrade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.Order;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Wallet;
import com.vikram.EquinoxTrade.model.WalletTransaction;
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

  public WalletController(
      WalletService walletService,
      UserService userService) {
    this.walletService = walletService;
    this.userService = userService;
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

    // Order order = orderService.findOrderById(orderId);

    Wallet wallet = walletService.payOrderPayment(order, user);

    return new ResponseEntity<>(wallet, HttpStatus.OK);
  }

}
