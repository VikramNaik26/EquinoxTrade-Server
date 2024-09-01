package com.vikram.EquinoxTrade.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.TwoFactorAuth;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.UserRepository;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private JwtService jwtService;

  public UserServiceImpl(
      UserRepository userRepository,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  private BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(12);

  @Override
  public UserEntity findUserByJwt(String jwt) {
    String email = jwtService.extractEmail(jwt);
    UserEntity user = userRepository.findByEmail(email);

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    return user;
  }

  @Override
  public UserEntity findUserByEmail(String email) {
    UserEntity user = userRepository.findByEmail(email);

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    return user;
  }

  @Override
  public UserEntity findUserById(Long id) {
    Optional<UserEntity> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    return user.get();
  }

  @Override
  public UserEntity enableTwoFactorAuth(VerificationType type, String sendTo, UserEntity user) {
    TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    twoFactorAuth.setEnabled(true);
    twoFactorAuth.setSendTo(type);

    user.setTwoFactorAuth(twoFactorAuth);

    return userRepository.save(user);
  }

  @Override
  public UserEntity updatePassword(UserEntity user, String newPassword) {
    user.setPassword(bEncoder.encode(newPassword));
    return userRepository.save(user);
  }

}
