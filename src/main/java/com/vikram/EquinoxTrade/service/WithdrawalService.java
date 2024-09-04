package com.vikram.EquinoxTrade.service;

import java.math.BigDecimal;
import java.util.List;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Withdrawal;

/**
 * WithdrawalService
 */
public interface WithdrawalService {

  public Withdrawal requestWithdrawal(BigDecimal amount, UserEntity user);

  public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept);

  public List<Withdrawal> getUsersWithdrawalHistory(UserEntity user);

  public List<Withdrawal> getAllWithdrawalRequest();
  
}
