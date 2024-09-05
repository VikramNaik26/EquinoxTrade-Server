package com.vikram.EquinoxTrade.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Wallet;
import com.vikram.EquinoxTrade.model.Withdrawal;
import com.vikram.EquinoxTrade.service.UserService;
import com.vikram.EquinoxTrade.service.WalletService;
import com.vikram.EquinoxTrade.service.WithdrawalService;

/**
 * WithdrawalController
 */
@RestController
public class WithdrawalController {

  private WithdrawalService withdrawalService;
  private WalletService walletService;
  private UserService userService;
  // private WalletTransactionService walletTransactionService;

  public WithdrawalController(
      WithdrawalService withdrawalService,
      WalletService walletService,
      UserService userService) {
    this.withdrawalService = withdrawalService;
    this.walletService = walletService;
    this.userService = userService;
    // this.walletTransactionService = walletTransactionService;
  }

  @PostMapping("/api/withdrawal/{amount}")
  public ResponseEntity<?> withdrawalRequest(
      @PathVariable BigDecimal amount,
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    Wallet userWallet = walletService.getUserWallet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalance(userWallet, withdrawal.getAmount().negate());

    // WalletTransaction walletTransaction =
    // walletTransactionService.createTransaction(
    // userWallet,
    // WalletTransactionType.WITHDRAWAL,
    // null,
    // "bank account withdrawal"
    // withdrawal.getAmount()
    // )

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @PostMapping("/api/admin/withdrawal/{id}/proceed/accept")
  public ResponseEntity<?> proceedWithWithdrawal(
      @PathVariable Long id,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);

    Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id, accept);

    Wallet userWallet = walletService.getUserWallet(user);

    if (!accept) {
      walletService.addBalance(userWallet, withdrawal.getAmount());
    }

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping("/api/withdrawal")
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    List<Withdrawal> withdrawals = withdrawalService.getUsersWithdrawalHistory(user);

    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }

  @GetMapping("/api/admin/withdrawal")
  public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequests() {
    List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();

    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }

}
