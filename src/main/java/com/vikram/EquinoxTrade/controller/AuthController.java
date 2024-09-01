package com.vikram.EquinoxTrade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.response.AuthResponse;
import com.vikram.EquinoxTrade.service.AuthService;

/**
 * AuthController
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
    return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> login(@RequestBody UserEntity user) {
    return new ResponseEntity<>(authService.verify(user), HttpStatus.OK);
  }

  @PostMapping("/verify-2fa/otp/{otp}")
  public ResponseEntity<AuthResponse> verifySigninOtp(
    @PathVariable String otp,
    @RequestParam String id) {
    return new ResponseEntity<>(authService.verifySigninOtp(otp, id), HttpStatus.OK);
  }

}
