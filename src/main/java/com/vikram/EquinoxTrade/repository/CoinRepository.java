package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.Coin;

/**
 * CoinRepository
 */
@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {

}
