package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.model.PaymentDetails;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * PaymentDetailsService
 */
public interface PaymentDetailsService {

  public PaymentDetails addPaymentDetails(
    String accountNumber,
    String accountHolderName,
    String ifscCode,
    String bankName,
    UserEntity user
  );

  public PaymentDetails getUserPaymentDetails(UserEntity user);
  
}
