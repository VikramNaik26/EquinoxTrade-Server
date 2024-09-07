package com.vikram.EquinoxTrade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.PaymentDetails;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.service.PaymentDetailsService;
import com.vikram.EquinoxTrade.service.UserService;

/**
 * PaymentDetailsController
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

  private UserService userService;
  private PaymentDetailsService paymentDetailsService;

  public PaymentDetailsController(
      UserService userService,
      PaymentDetailsService paymentDetailsService) {
    this.userService = userService;
    this.paymentDetailsService = paymentDetailsService;
  }

  @PostMapping("/payment-details")
  public ResponseEntity<PaymentDetails> addPaymentDetails(
      @RequestBody PaymentDetails paymenyDetailsRequest,
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
        paymenyDetailsRequest.getAccountNumber(),
        paymenyDetailsRequest.getAccountHolderName(),
        paymenyDetailsRequest.getIfscCode(),
        paymenyDetailsRequest.getBankName(),
        user);

    return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
  }

  @GetMapping("/payment-details")
  public ResponseEntity<PaymentDetails> getPaymentDetails(
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);

    return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
  }
}
