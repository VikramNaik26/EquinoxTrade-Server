package com.vikram.EquinoxTrade.service;

/**
 * EmailService
 */
public interface EmailService {

  public void sendVerificationOtpEmail(String email, String otp);

  public void sendTwoFactorAuthSuccessEmail(String sendTo);

}
