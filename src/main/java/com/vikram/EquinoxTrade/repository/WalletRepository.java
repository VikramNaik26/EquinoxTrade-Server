package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.Wallet;

/**
 * WalletRepository
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Wallet findByUserId(Long userId);

}
