package com.vikram.EquinoxTrade.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.exception.AuthExcetion;
import com.vikram.EquinoxTrade.model.TwoFactorOTP;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.UserRepository;
import com.vikram.EquinoxTrade.response.AuthResponse;
import com.vikram.EquinoxTrade.service.AuthService;
import com.vikram.EquinoxTrade.service.EmailService;
import com.vikram.EquinoxTrade.service.JwtService;
import com.vikram.EquinoxTrade.service.TwoFactorOtpService;
import com.vikram.EquinoxTrade.service.WatchlistService;
import com.vikram.EquinoxTrade.utils.OtpUtils;

/**
 * AuthService
 */
@Service
public class AuthServiceImpl implements AuthService {

  private AuthenticationManager authManager;
  private UserRepository userRepository;
  private JwtService jwtService;
  private TwoFactorOtpService tOtpService;
  private EmailService emailService;
  private WatchlistService watchlistService;

  public AuthServiceImpl(
      UserRepository userRepository,
      AuthenticationManager authManager,
      JwtService jwtService,
      TwoFactorOtpService tOtpService,
      EmailService emailService,
      WatchlistService watchlistService) {
    this.userRepository = userRepository;
    this.authManager = authManager;
    this.jwtService = jwtService;
    this.tOtpService = tOtpService;
    this.emailService = emailService;
    this.watchlistService = watchlistService;
  }

  private BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(12);

  public UserEntity register(UserEntity user) {
    UserEntity existingUser = userRepository.findByEmail(user.getEmail());

    if (existingUser != null) {
      throw new AuthExcetion("Email already exists");
    }

    user.setPassword(bEncoder.encode(user.getPassword()));
    UserEntity savedUser = userRepository.save(user);

    watchlistService.createWatchlist(savedUser);

    return savedUser;
  }

  public AuthResponse verify(UserEntity loginAttempt) {
    UserEntity user = userRepository.findByEmail(loginAttempt.getEmail());

    if (user == null) {
      throw new AuthExcetion("Email does not exist");
    }

    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginAttempt.getEmail(), loginAttempt.getPassword()));

    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(user.getEmail());

      if (user.getTwoFactorAuth().isEnabled()) {
        return handleTwoFactorAuth(user, token);
      }

      return createSuccessfulAuthResponse(token);
    }

    return createFailedAuthResponse();
  }

  private AuthResponse handleTwoFactorAuth(UserEntity user, String token) {
    AuthResponse response = new AuthResponse();
    response.setMessage("Two Factor Authentication OTP sent to your email");
    response.setTwoFactorAuthEnabled(true);

    try {
      String otp = OtpUtils.generateOtp();

      TwoFactorOTP oldTwoFactorOTP = tOtpService.findByUserId(user.getId());
      if (oldTwoFactorOTP != null) {
        tOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
      }

      TwoFactorOTP newTwoFactorOTP = tOtpService.createTwoFactorOtp(user, otp, token);
      emailService.sendVerificationOtpEmail(user.getEmail(), otp);
      response.setSession(newTwoFactorOTP.getId());

      return response;
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
      throw new AuthExcetion("Error generating OTP");
    }
  }

  private AuthResponse createSuccessfulAuthResponse(String token) {
    AuthResponse response = new AuthResponse();
    response.setToken(token);
    response.setSuccess(true);
    response.setMessage("Login success");

    return response;
  }

  private AuthResponse createFailedAuthResponse() {
    AuthResponse response = new AuthResponse();
    response.setSuccess(false);
    response.setMessage("Invalid credentials");

    return response;
  }

  public AuthResponse verifySigninOtp(String otp, String id) {
    TwoFactorOTP twoFactorOTP = tOtpService.findById(id);

    if (tOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
      tOtpService.deleteTwoFactorOtp(twoFactorOTP);

      AuthResponse response = new AuthResponse();
      response.setSuccess(true);
      response.setMessage("Two Factor Authentication verified");
      response.setToken(twoFactorOTP.getJwt()); 

      return response;
    }

    throw new AuthExcetion("Invalid OTP");
  }

}

