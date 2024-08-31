package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.TwoFactorOTP;

/**
 * TwoFactorOtpRepository
 */
@Repository
public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

  TwoFactorOTP findByUserId(Long userId);

}
