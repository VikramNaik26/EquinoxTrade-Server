package com.vikram.EquinoxTrade.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.exception.AuthExcetion;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.UserRepository;
import com.vikram.EquinoxTrade.response.AuthResponse;

/**
 * AuthService
 */
@Service
public class AuthService {

  private AuthenticationManager authManager;
  private UserRepository userRepository;
  private JwtService jwtService;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authManager,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.authManager = authManager;
    this.jwtService = jwtService;
  }

  private BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(12);

  public UserEntity register(UserEntity user) {
    UserEntity isUserEntity = userRepository.findByEmail(user.getEmail());

    if (isUserEntity != null) {
      throw new AuthExcetion("Email already exist");
    }

    user.setPassword(bEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  public AuthResponse verify(UserEntity user) {
    if (userRepository.findByEmail(user.getEmail()) == null) {
      throw new AuthExcetion("Email does not exist");
    }

    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(user.getEmail());

      AuthResponse response = new AuthResponse();
      response.setToken(token);
      response.setSuccess(true);
      response.setMessage("Login success");
      return response;
    }

    AuthResponse response = new AuthResponse();
    response.setSuccess(false);
    response.setMessage("Invalid credentials");
    return response;
  }

}
