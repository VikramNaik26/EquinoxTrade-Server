package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.OrderItem;

/**
 * OrderItemRepository
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
