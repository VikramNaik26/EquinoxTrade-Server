package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.response.AuthResponse;

/**
 * AuthService
 */
public interface AuthService {

  public UserEntity register(UserEntity user); 

  public AuthResponse verify(UserEntity loginAttempt);

  public AuthResponse verifySigninOtp(String otp, String id);

}
