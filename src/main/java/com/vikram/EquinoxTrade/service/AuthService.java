package com.vikram.EquinoxTrade.service;

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
import com.vikram.EquinoxTrade.utils.OtpUtils;

/**
 * AuthService
 */
@Service
public class AuthService {

  private AuthenticationManager authManager;
  private UserRepository userRepository;
  private JwtService jwtService;
  private TwoFactorOtpService tOtpService;
  private EmailService emailService;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authManager,
      JwtService jwtService,
      TwoFactorOtpService tOtpService,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.authManager = authManager;
    this.jwtService = jwtService;
    this.tOtpService = tOtpService;
    this.emailService = emailService;
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

      if (user.getTwoFactorAuth().isEnabled()) {
        AuthResponse response = new AuthResponse();

        response.setMessage("Two Factor Authentication Enabled");
        response.setTwoFactorAuthEnabled(true);

        try {
          String otp = OtpUtils.generateOtp();
          System.out.println(otp);

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
        }

      }

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
