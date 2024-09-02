package com.vikram.EquinoxTrade.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.VerificationCode;
import com.vikram.EquinoxTrade.repository.VerificationCodeRepository;
import com.vikram.EquinoxTrade.service.VerificationCodeService;
import com.vikram.EquinoxTrade.utils.OtpUtils;

/**
 * VerificationCodeServiceImpl
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

  private VerificationCodeRepository vRepository;

  public VerificationCodeServiceImpl(VerificationCodeRepository vRepository) {
    this.vRepository = vRepository;
  }

  @Override
  public VerificationCode sendVerificationOtp(UserEntity user, VerificationType type) {
    VerificationCode vCode = new VerificationCode();

    try {
      vCode.setOtp(OtpUtils.generateOtp());
      vCode.setVerificationaType(type);
      vCode.setUser(user);

      return vRepository.save(vCode);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new RuntimeException("Error generating OTP");
    }
  }

  @Override
  public VerificationCode getVerificationCode(Long id) {
    Optional<VerificationCode> vCode = vRepository.findById(id);

    if (vCode.isPresent()) {
      return vCode.get();
    }

    throw new RuntimeException("Verification code not found");
  }

  @Override
  public VerificationCode getVerificationCodeByUser(Long userId) {
    return vRepository.findByUserId(userId);
  }

  @Override
  public void deleteVerificationCode(VerificationCode verificationCode) {
    vRepository.delete(verificationCode);
  }

  @Override
  public Boolean verifyTwoFactorOtp(VerificationCode verificationCode, String otp) {
    if (verificationCode.getOtp().equals(otp)) {
      return true;
    }
    return false;
  }

}
