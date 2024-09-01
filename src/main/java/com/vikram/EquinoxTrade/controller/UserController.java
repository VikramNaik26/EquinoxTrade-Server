package com.vikram.EquinoxTrade.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.ForgotPasswordToken;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.VerificationCode;
import com.vikram.EquinoxTrade.request.ForgotPasswordTokenRequest;
import com.vikram.EquinoxTrade.request.ResetPasswordRequest;
import com.vikram.EquinoxTrade.response.AuthResponse;
import com.vikram.EquinoxTrade.service.EmailService;
import com.vikram.EquinoxTrade.service.ForgotPasswordService;
import com.vikram.EquinoxTrade.service.UserService;
import com.vikram.EquinoxTrade.service.VerificationCodeService;
import com.vikram.EquinoxTrade.utils.OtpUtils;

/**
 * UserController
 */
@RestController
public class UserController {

  private UserService userService;
  private EmailService emailService;
  private VerificationCodeService verificationCodeService;
  private ForgotPasswordService forgotPasswordService;

  public UserController(
      UserService userService,
      EmailService emailService,
      VerificationCodeService verificationCodeService,
      ForgotPasswordService forgotPasswordService) {
    this.userService = userService;
    this.emailService = emailService;
    this.verificationCodeService = verificationCodeService;
    this.forgotPasswordService = forgotPasswordService;
  }

  @GetMapping("/api/users/profile")
  public ResponseEntity<UserEntity> getUserProfile(@RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("/api/users/verification/{verificationType}/send-otp")
  public ResponseEntity<String> sendOtp(
      @RequestHeader("Authorization") String jwt,
      @PathVariable VerificationType verificationType) {
    UserEntity user = userService.findUserByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

    if (verificationCode == null) {
      verificationCode = verificationCodeService.sendVerificationOtp(user, verificationType);
    }

    if (verificationType.equals(VerificationType.EMAIL)) {
      emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
    }

    return new ResponseEntity<>("Verification OTP sent", HttpStatus.OK);
  }

  @PatchMapping("/api/users/enable-two-factor-auth/verify-otp/{otp}")
  public ResponseEntity<UserEntity> enableTwoFactorAuth(
      @RequestHeader("Authorization") String jwt,
      @PathVariable String otp) {
    UserEntity user = userService.findUserByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
    String sendTo = verificationCode.getVerificationaType().equals(VerificationType.EMAIL)
        ? verificationCode.getEmail()
        : verificationCode.getMobile();

    boolean isVerified = verificationCodeService.verifyTwoFactorOtp(verificationCode, otp);

    if (isVerified) {
      user = userService.enableTwoFactorAuth(verificationCode.getVerificationaType(), sendTo, user);
      verificationCodeService.deleteVerificationCode(verificationCode);
      emailService.sendTwoFactorAuthSuccessEmail(sendTo);

      return new ResponseEntity<>(user, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/auth/users/reset-passsword/send-otp")
  public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
      @RequestBody ForgotPasswordTokenRequest request) {
    UserEntity user = userService.findUserByEmail(request.getSendTo());

    try {
      String otp = OtpUtils.generateOtp();
      UUID uuid = UUID.randomUUID();
      String id = uuid.toString();

      ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

      if (token != null) {
        forgotPasswordService.deleteToken(token);
      }

      token = forgotPasswordService.createForgotPasswordToken(
          user,
          id,
          otp,
          request.getVerificationType(),
          request.getSendTo());

      if (request.getVerificationType().equals(VerificationType.EMAIL)) {
        emailService.sendVerificationOtpEmail(request.getSendTo(), token.getOtp());
      }

      AuthResponse response = new AuthResponse();

      response.setMessage("Forgot Password OTP sent");
      response.setToken(token.getId());
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new RuntimeException("Error generating OTP");
    }
  }

  @PatchMapping("/auth/users/reset-passsword/verify-otp")
  public ResponseEntity<String> verifyForgotPasswordOtp(
      @RequestParam String id,
      @RequestBody ResetPasswordRequest request) {
    ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
    Boolean isVerified = forgotPasswordService.verifyToken(forgotPasswordToken, request.getOtp());

    if (isVerified) {
      userService.updatePassword(forgotPasswordToken.getUser(), request.getPassword());
      forgotPasswordService.deleteToken(forgotPasswordToken);

      return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

    return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
  }
}
