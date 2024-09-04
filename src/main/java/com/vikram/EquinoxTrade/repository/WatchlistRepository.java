package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.Watchlist;

/**
 * WatchlistRepository
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

  public Watchlist findByUserId(Long userId);
  
}
