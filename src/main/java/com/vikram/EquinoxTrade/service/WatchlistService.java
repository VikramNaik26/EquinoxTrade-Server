package com.vikram.EquinoxTrade.service;

import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Watchlist;

/**
 * WatchlistService
 */
public interface WatchlistService {

  public Watchlist findUserWatchlist(Long userId);

  public Watchlist createWatchlist(UserEntity user);

  public Watchlist findById(Long id);
 
  Coin toggleItemToWatchlist(UserEntity user, Coin coin);
}
