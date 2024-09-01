package com.vikram.EquinoxTrade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.domain.VerificationType;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.VerificationCode;
import com.vikram.EquinoxTrade.service.EmailService;
import com.vikram.EquinoxTrade.service.UserService;
import com.vikram.EquinoxTrade.service.VerificationCodeService;

/**
 * UserController
 */
@RestController
public class UserController {

  private UserService userService;
  private EmailService emailService;
  private VerificationCodeService verificationCodeService;

  public UserController(
      UserService userService,
      EmailService emailService,
      VerificationCodeService verificationCodeService) {
    this.userService = userService;
    this.emailService = emailService;
    this.verificationCodeService = verificationCodeService;
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

}
