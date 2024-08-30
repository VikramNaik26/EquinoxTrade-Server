package com.vikram.EquinoxTrade.model;

import com.vikram.EquinoxTrade.domain.VerificationaType;

import lombok.Data;

@Data
public class TwoFactorAuth {

  private boolean isEnabled = false;
  private VerificationaType sendTo;
  
}
