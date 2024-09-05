package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikram.EquinoxTrade.model.PaymentDetails;

/**
 * PaymentDetailsRepository
 */
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

  PaymentDetails findByUserId(Long userId);
  
}
