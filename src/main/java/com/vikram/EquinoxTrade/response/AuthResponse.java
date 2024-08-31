package com.vikram.EquinoxTrade.response;

/**
 * AuthResponse
 */
public class AuthResponse {

  private String token;
  private boolean success;
  private String message;
  private boolean isTwoFactorAuthEnabled;
  private String session;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isTwoFactorAuthEnabled() {
    return isTwoFactorAuthEnabled;
  }

  public void setTwoFactorAuthEnabled(boolean isTwoFactorAuthEnabled) {
    this.isTwoFactorAuthEnabled = isTwoFactorAuthEnabled;
  }

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }

}
