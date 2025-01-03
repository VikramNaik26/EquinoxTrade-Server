package com.vikram.EquinoxTrade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.TradeOrder;

/**
 * OrderRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<TradeOrder, Long> {

  public List<TradeOrder> findByUserId(Long UserId);

}
