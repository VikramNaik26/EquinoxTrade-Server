package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * UserService
 */
public interface UserService {

  public UserEntity findUserByJwt(String jwt);

  public UserEntity findUserByEmail(String email);

  public UserEntity findUserById(Long id);

  public UserEntity enableTwoFactorAuth(VerificationType type, String sendTo, UserEntity user);

  public UserEntity updatePassword(UserEntity user, String newPassword);

}
