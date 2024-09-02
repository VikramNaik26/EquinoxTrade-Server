package com.vikram.EquinoxTrade.service;

import java.math.BigDecimal;

import com.vikram.EquinoxTrade.model.Order;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Wallet;

/**
 * WalletService
 */
public interface WalletService {

  public Wallet getUserWallet(UserEntity user);
  
  public Wallet addBalance(Wallet wallet, BigDecimal amount);

  public Wallet findWalletById(Long id);

  public Wallet walletToWalletTransfer(UserEntity sender, Wallet recieverWallet, BigDecimal amount);

  public Wallet payOrderPayment(Order order, UserEntity user);

}
