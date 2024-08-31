package com.vikram.EquinoxTrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * EmailService
 */
@Service
public class EmailService {

  private JavaMailSender mailSender;

  public EmailService() {}

  @Autowired
  public EmailService(JavaMailSender mailSender) {
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

}
