package com.vikram.EquinoxTrade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.Withdrawal;

/**
 * WithdrawalRepository
 */
@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

  public List<Withdrawal> findByUserId(Long userId);
  
}
