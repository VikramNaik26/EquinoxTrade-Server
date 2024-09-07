package com.vikram.EquinoxTrade.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.vikram.EquinoxTrade.domain.PaymentMethod;
import com.vikram.EquinoxTrade.model.PaymentOrder;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.response.PaymentResponse;
import com.vikram.EquinoxTrade.service.PaymentService;
import com.vikram.EquinoxTrade.service.UserService;

/**
 * PaymentController
 */
@RestController
@RequestMapping("/api")
public class PaymentController {

  private UserService userService;
  private PaymentService paymentService;

  public PaymentController(UserService userService, PaymentService paymentService) {
    this.userService = userService;
    this.paymentService = paymentService;
  }

  @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
  public ResponseEntity<PaymentResponse> paymentHandler(
      @PathVariable PaymentMethod paymentMethod,
      @PathVariable Long amount,
      @RequestHeader("Authorization") String jwt) throws StripeException {
    UserEntity user = userService.findUserByJwt(jwt);

    PaymentResponse paymentResponse;

    PaymentOrder order = paymentService.createOrder(user, BigDecimal.valueOf(amount), paymentMethod);

    if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
      paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
    } else {
      paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());
    }

    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
  }

}
