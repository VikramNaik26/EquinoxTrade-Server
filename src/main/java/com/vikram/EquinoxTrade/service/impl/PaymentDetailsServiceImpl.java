package com.vikram.EquinoxTrade.service.impl;

import com.vikram.EquinoxTrade.model.PaymentDetails;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.PaymentDetailsRepository;
import com.vikram.EquinoxTrade.service.PaymentDetailsService;

/**
 * PaymentDetailsServiceImpl
 */
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

  private PaymentDetailsRepository paymentDetailsRepository;

  public PaymentDetailsServiceImpl(
      PaymentDetailsRepository paymentDetailsRepository) {
    this.paymentDetailsRepository = paymentDetailsRepository;
  }

  @Override
  public PaymentDetails addPaymentDetails(
      String accountNumber,
      String accountHolderName,
      String ifscCode,
      String bankName, UserEntity user) {
    PaymentDetails paymentDetails = new PaymentDetails();

    paymentDetails.setAccountNumber(accountNumber);
    paymentDetails.setAccountHolderName(accountHolderName);
    paymentDetails.setIfscCode(ifscCode);
    paymentDetails.setBankName(bankName);
    paymentDetails.setUser(user);

    return paymentDetailsRepository.save(paymentDetails);
  }

  @Override
  public PaymentDetails getUserPaymentDetails(UserEntity user) {
    return paymentDetailsRepository.findByUserId(user.getId());
  }

}
