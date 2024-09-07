package com.vikram.EquinoxTrade.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vikram.EquinoxTrade.domain.PaymentMethod;
import com.vikram.EquinoxTrade.domain.PaymentOrderStatus;
import com.vikram.EquinoxTrade.model.PaymentOrder;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.PaymentRepositoty;
import com.vikram.EquinoxTrade.response.PaymentResponse;
import com.vikram.EquinoxTrade.service.PaymentService;

import net.minidev.json.JSONObject;

/**
 * PaymentServiceImpl
 */
@Service
public class PaymentServiceImpl implements PaymentService {

  private PaymentRepositoty paymentRepositoty;

  @Value("${stripe.api.key}")
  private String stripeSecretKey;

  // @Value("${razorpay.api.key}")
  // private String apiKey;

  // @Value("${razorpay.api.secret}")
  // private String apiSecretKey;

  public PaymentServiceImpl(PaymentRepositoty paymentRepositoty) {
    this.paymentRepositoty = paymentRepositoty;
  }

  @Override
  public PaymentOrder createOrder(UserEntity user, BigDecimal amount, PaymentMethod paymentMethod) {
    PaymentOrder paymentOrder = new PaymentOrder();

    paymentOrder.setUser(user);
    paymentOrder.setAmount(amount);
    paymentOrder.setPaymentMethod(paymentMethod);

    return paymentRepositoty.save(paymentOrder);
  }

  @Override
  public PaymentOrder getPaymentOrderById(Long id) {
    return paymentRepositoty.findById(id).orElseThrow(() -> new RuntimeException("Payment order not found"));
  }

  @Override
  public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
    /* if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
      if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecretKey);
        Payment payment = razorpayClient.payments.fetch(paymentId);

        Integer amount = payment.get("amount");
        String status = payment.get("status");

        if (status.equals("captured")) {
          paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
          return true;
        }

        paymentOrder.setStatus(PaymentOrderStatus.FAILED);
        paymentRepositoty.save(paymentOrder);
        return false;
      }

      paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
      paymentRepositoty.save(paymentOrder);
      return true;
    } */

    return false;
  }

  @Override
  public PaymentResponse createStripePaymentLink(UserEntity user, Long amount, Long orderId) throws StripeException {
    Stripe.apiKey = stripeSecretKey;

    SessionCreateParams params = SessionCreateParams.builder()
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
        .setCancelUrl("http://localhost:5173/payment/cancel")
        .addLineItem(SessionCreateParams.LineItem.builder()
            .setQuantity(1L)
            .setPriceData(
                SessionCreateParams.LineItem.PriceData
                    .builder()
                    .setCurrency("usd")
                    .setUnitAmount(amount * 100)
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData
                            .builder()
                            .setName("EquinoxTrade")
                            .build())
                    .build())
            .build())
        .build();

    Session session = Session.create(params);

    System.out.println("Session__________" + session);
    PaymentResponse paymentResponse = new PaymentResponse();

    paymentResponse.setPaymentUrl(session.getUrl());

    return paymentResponse;
  }

  @Override
  public PaymentResponse createRazorpayPaymentLink(UserEntity user, Long amount) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createRazorpayPaymentLink'");

    /*
     * amount = amount * 100;
     * 
     * try {
     * RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecretKey);
     * 
     * JSONObject paymentLinkRequest = new JSONObject();
     * paymentLinkRequest.put("amount", amount);
     * paymentLinkRequest.put("currency", "INR");
     * 
     * JSONObject customer = new JSONObject();
     * customer.put("name", user.getFullname());
     * customer.put("email", user.getEmail());
     * paymentLinkRequest.put("customer", customer);
     * 
     * JSONObject notify = new JSONObject();
     * notify.put("email", true);
     * paymentLinkRequest.put("notify", notify);
     * 
     * paymentLinkRequest.put("remainder_enabled", true);
     * 
     * paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
     * paymentLinkRequest.put("callback_method", "GET");
     * 
     * PaymentLink paymentLink =
     * razorpayClient.paymentLink.create(paymentLinkRequest);
     * 
     * String paymentLinkId = paymentLink.get("id");
     * String paymentLinkUrl = paymentLink.get("short_url");
     * 
     * PaymentResponse response = new PaymentResponse();
     * 
     * response.setPaymentUrl(paymentLinkUrl);
     * 
     * return response;
     * } catch (RazorpayException e) {
     * System.out.println(e.getMessage());
     * throw new RuntimeException(e.getMessage());
     * }
     */
  }

}
