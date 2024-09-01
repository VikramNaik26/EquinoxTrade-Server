package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.model.TwoFactorOTP;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * TwoFactorOtpService
 */
public interface TwoFactorOtpService {

  TwoFactorOTP createTwoFactorOtp(UserEntity user, String otp, String jwt);

  TwoFactorOTP findByUserId(Long userId);

  TwoFactorOTP findById(String id);

  boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

  void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

}
