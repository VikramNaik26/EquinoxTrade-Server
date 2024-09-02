package com.vikram.EquinoxTrade.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.OrderType;
import com.vikram.EquinoxTrade.model.Order;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Wallet;
import com.vikram.EquinoxTrade.repository.WalletRepository;
import com.vikram.EquinoxTrade.service.WalletService;

/**
 * WalletServiceImpl
 */
@Service
public class WalletServiceImpl implements WalletService {

  private WalletRepository walletRepository;

  public WalletServiceImpl(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Override
  public Wallet getUserWallet(UserEntity user) {
    Wallet wallet = walletRepository.findByUserId(user.getId());

    if (wallet == null) {
      wallet = new Wallet();
      wallet.setUser(user);
      wallet = walletRepository.save(wallet);
    }

    return wallet;
  }

  @Override
  public Wallet addBalance(Wallet wallet, BigDecimal amount) {
    BigDecimal balance = wallet.getBalance();
    wallet.setBalance(balance.add(amount));

    return walletRepository.save(wallet);
  }

  @Override
  public Wallet findWalletById(Long id) {
    Optional<Wallet> wallet = walletRepository.findById(id);
    if (wallet.isPresent()) {
      return wallet.get();
    }

    throw new RuntimeException("Wallet not found");
  }

  @Override
  public Wallet walletToWalletTransfer(UserEntity sender, Wallet recieverWallet, BigDecimal amount) {
    Wallet senderWallet = getUserWallet(sender);

    if (senderWallet.getBalance().compareTo(amount) < 0) {
      throw new RuntimeException("Insufficient balance");
    }

    senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
    recieverWallet.setBalance(recieverWallet.getBalance().add(amount));

    walletRepository.save(senderWallet);
    walletRepository.save(recieverWallet);

    return senderWallet;
  }

  @Override
  public Wallet payOrderPayment(Order order, UserEntity user) {
    Wallet userWallet = getUserWallet(user);

    if (order.getOrderType().equals(OrderType.BUY)) {
      BigDecimal newBalance = userWallet.getBalance().subtract(order.getPrice());

      if (newBalance.compareTo(order.getPrice()) < 0) {
        throw new RuntimeException("Insufficient balance");
      }

      userWallet.setBalance(newBalance);
    } else {
      userWallet.setBalance(userWallet.getBalance().add(order.getPrice()));
    }

    return walletRepository.save(userWallet);
  }

}
