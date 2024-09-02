package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.exception.AuthExcetion;
import com.vikram.EquinoxTrade.model.TwoFactorOTP;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.UserRepository;
import com.vikram.EquinoxTrade.response.AuthResponse;
import com.vikram.EquinoxTrade.utils.OtpUtils;

/**
 * AuthService
 */
public interface AuthService {

  public UserEntity register(UserEntity user); 

  public AuthResponse verify(UserEntity loginAttempt);

  public AuthResponse verifySigninOtp(String otp, String id);

}
