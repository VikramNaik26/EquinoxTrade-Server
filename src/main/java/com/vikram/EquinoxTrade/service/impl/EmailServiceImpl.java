package com.vikram.EquinoxTrade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * EmailService
 */
@Service
public class EmailServiceImpl implements EmailService {

  private JavaMailSender mailSender;

  public EmailServiceImpl() {
  }

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendVerificationOtpEmail(String email, String otp) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

    String subject = "Verify OTP";
    String text = "Your OTP is " + otp;

    try {
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(text, true);
      mimeMessageHelper.setTo(email);
      mimeMessageHelper.setFrom("V3g6H@example.com");

      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new MailSendException("Failed to send email", e);
    }

  }

  public void sendTwoFactorAuthSuccessEmail(String sendTo) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

    String subject = "Two Factor Authentication Success";
    String text = "Two Factor Authentication Success";

    try {
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(text, true);
      mimeMessageHelper.setTo(sendTo);
      mimeMessageHelper.setFrom("V3g6H@example.com");
    } catch (MessagingException e) {
      throw new MailSendException("Failed to send email", e);
    }

    mailSender.send(mimeMessage);
  }

}
