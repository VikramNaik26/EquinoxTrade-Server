package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.PaymentOrder;

/**
 * PaymentRepositoty
 */
@Repository
public interface PaymentRepositoty extends JpaRepository<PaymentOrder, Long> {

}
