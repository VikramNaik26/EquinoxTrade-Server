package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.ForgotPasswordToken;

/**
 * ForgotPasswordRepository
 */
@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

  ForgotPasswordToken findByUserId(Long userId);

}
