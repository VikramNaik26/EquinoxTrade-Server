package com.vikram.EquinoxTrade.service.impl;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Watchlist;
import com.vikram.EquinoxTrade.repository.WatchlistRepository;
import com.vikram.EquinoxTrade.service.WatchlistService;

/**
 * WatchlistServiceImpl
 */
@Service
public class WatchlistServiceImpl implements WatchlistService {

  private WatchlistRepository watchlistRepository;

  public WatchlistServiceImpl(WatchlistRepository watchlistRepository) {
    this.watchlistRepository = watchlistRepository;
  }

  @Override
  public Watchlist findUserWatchlist(Long userId) {
    Watchlist watchlist = watchlistRepository.findByUserId(userId);

    if (watchlist == null) {
      throw new UnsupportedOperationException("Watchlist not found for user " + userId);
    }

    return watchlist;
  }

  @Override
  public Watchlist createWatchlist(UserEntity user) {
    Watchlist watchlist = new Watchlist();
    watchlist.setUser(user);

    return watchlistRepository.save(watchlist);
  }

  @Override
  public Watchlist findById(Long id) {
    return watchlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Watchlist not found"));
  }

  @Override
  public Coin addItemToWatchlist(UserEntity user, Coin coin) {
    Watchlist watchlist = findUserWatchlist(user.getId());

    if(!watchlist.getCoins().contains(coin)) {
      watchlist.getCoins().add(coin);
    }
    watchlist.getCoins().remove(coin);
    watchlistRepository.save(watchlist);

    return coin;
  }

}
