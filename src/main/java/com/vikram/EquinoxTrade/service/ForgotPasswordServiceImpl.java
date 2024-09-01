package com.vikram.EquinoxTrade.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.ForgotPasswordToken;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.ForgotPasswordRepository;

/**
 * ForgotPasswordServiceImpl
 */
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

  private ForgotPasswordRepository fPasswordRepository;

  public ForgotPasswordServiceImpl(ForgotPasswordRepository fPasswordRepository) {
    this.fPasswordRepository = fPasswordRepository;
  }

  @Override
  public ForgotPasswordToken createForgotPasswordToken(
      UserEntity user,
      String id,
      String otp,
      VerificationType verificationType,
      String sendTo) {
    ForgotPasswordToken token = new ForgotPasswordToken();
    token.setUser(user);
    token.setSendTo(sendTo);
    token.setOtp(otp);
    token.setVerificationType(verificationType);
    token.setId(id);
    return fPasswordRepository.save(token);
  }

  @Override
  public ForgotPasswordToken findById(String id) {
    Optional<ForgotPasswordToken> token = fPasswordRepository.findById(id);
    if (token.isPresent()) {
      return token.get();
    }

    throw new RuntimeException("Token not found");
  }

  @Override
  public ForgotPasswordToken findByUser(Long userId) {
    return fPasswordRepository.findByUserId(userId);
  }

  @Override
  public void deleteToken(ForgotPasswordToken token) {
    fPasswordRepository.delete(token);
  }

  @Override
  public Boolean verifyToken(ForgotPasswordToken token, String otp) {
    return token.getOtp().equals(otp);
  }

}
