package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.ForgotPasswordToken;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * ForgotPasswordService
 */
public interface ForgotPasswordService {

  ForgotPasswordToken createForgotPasswordToken(
      UserEntity user,
      String id,
      String otp,
      VerificationType verificationType,
      String sendTo);

  ForgotPasswordToken findById(String id);

  ForgotPasswordToken findByUser(Long userId);

  Boolean verifyToken(ForgotPasswordToken token, String otp);

  void deleteToken(ForgotPasswordToken token);
}
