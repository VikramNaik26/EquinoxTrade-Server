package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.VerificationCode;

/**
 * VerificationCodeService
 */
public interface VerificationCodeService {

  VerificationCode sendVerificationOtp(UserEntity user, VerificationType type);

  VerificationCode getVerificationCode(Long id);

  VerificationCode getVerificationCodeByUser(Long userId);

  Boolean verifyTwoFactorOtp(VerificationCode verificationCode, String otp);

  void deleteVerificationCode(VerificationCode verificationCode);
}
