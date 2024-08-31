package com.vikram.EquinoxTrade.service;

import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtService
 */
@Service
public class JwtService {

  private String secretKey;

  public JwtService() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
      SecretKey sk = keyGenerator.generateKey();

      secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public String generateToken(String email) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts
        .builder()
        .claims()
        .add(claims)
        .subject(email)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .and()
        .signWith(getKey())
        .compact();
  }

  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractEmail(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean validateToken(String token, String email) {
    final String tokenEmail = extractEmail(token);
    return (tokenEmail.equals(email) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }

  // public List<GrantedAuthority> getAuthorities(String token) {
  //   String authorities = String.valueOf(extractAllClaims(token).get("authorities"));
  //   List<GrantedAuthority> simpleGrantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

  //   return simpleGrantedAuthorities;
  // }

}
