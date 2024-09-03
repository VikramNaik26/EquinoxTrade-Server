package com.vikram.EquinoxTrade.service;

import java.math.BigDecimal;

import com.vikram.EquinoxTrade.model.TradeOrder;
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

  public Wallet payOrderPayment(TradeOrder order, UserEntity user);

}
