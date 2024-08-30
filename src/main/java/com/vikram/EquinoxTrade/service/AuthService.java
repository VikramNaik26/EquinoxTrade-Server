package com.vikram.EquinoxTrade.service;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.UserRepository;

/**
 * AuthService
 */
@Service
public class AuthService {

  private UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity register(UserEntity user) {
    
    return userRepository.save(user);
  }

}
