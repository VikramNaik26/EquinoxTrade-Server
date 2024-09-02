package com.vikram.EquinoxTrade.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * JwtService
 */
public interface JwtService {

  public String generateToken(String email);

  public String extractEmail(String token);

  public boolean validateToken(String token, UserDetails userDetails);

}
