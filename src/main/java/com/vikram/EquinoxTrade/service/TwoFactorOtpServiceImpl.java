package com.vikram.EquinoxTrade.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.model.TwoFactorOTP;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.TwoFactorOtpRepository;

/**
 * TwoFactorOtpServiceImpl
 */
@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {
  private TwoFactorOtpRepository tRepository;

  public TwoFactorOtpServiceImpl(TwoFactorOtpRepository tRepository) {
    this.tRepository = tRepository;
  }

  @Override
  public TwoFactorOTP createTwoFactorOtp(UserEntity user, String otp, String jwt) {
    UUID uuid = UUID.randomUUID();

    String id = uuid.toString();

    TwoFactorOTP twoFactorOTP = new TwoFactorOTP();

    twoFactorOTP.setId(id);
    twoFactorOTP.setOtp(otp);
    twoFactorOTP.setUser(user);
    twoFactorOTP.setJwt(jwt);

    return tRepository.save(twoFactorOTP);
  }

  @Override
  public TwoFactorOTP findByUserId(Long userId) {
    return tRepository.findByUserId(userId);
  }

  @Override
  public TwoFactorOTP findById(String id) {
    Optional<TwoFactorOTP> otp = tRepository.findById(id);

    return otp.orElse(null);
  }

  @Override
  public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
    return twoFactorOTP.getOtp().equals(otp);
  }

  @Override
  public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
    tRepository.delete(twoFactorOTP);
  }

}
