package com.vikram.EquinoxTrade.service;

import java.math.BigDecimal;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.vikram.EquinoxTrade.domain.PaymentMethod;
import com.vikram.EquinoxTrade.model.PaymentOrder;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.response.PaymentResponse;

/**
 * PaymentService
 */
public interface PaymentService {

  public PaymentOrder createOrder(UserEntity user, BigDecimal amount, PaymentMethod paymentMethod);

  public PaymentOrder getPaymentOrderById(Long id);

  public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

  public PaymentResponse createStripePaymentLink(UserEntity user, Long amount, Long orderId) throws StripeException;

  public PaymentResponse createRazorpayPaymentLink(UserEntity user, Long amount, Long orderId);

}
