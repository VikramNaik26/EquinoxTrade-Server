package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.VerificationCode;

/**
 * VerificationCodeRepository
 */
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

  public VerificationCode findByUserId(Long userId);

}
