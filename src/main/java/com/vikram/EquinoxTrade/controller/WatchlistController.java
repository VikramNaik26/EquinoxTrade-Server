package com.vikram.EquinoxTrade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Watchlist;
import com.vikram.EquinoxTrade.service.CoinService;
import com.vikram.EquinoxTrade.service.UserService;
import com.vikram.EquinoxTrade.service.WatchlistService;

/**
 * WatchlistController
 */
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

  private WatchlistService watchlistService;
  private UserService userService;
  private CoinService coinService;

  public WatchlistController(
      WatchlistService watchlistService,
      UserService userService,
      CoinService coinService) {
    this.watchlistService = watchlistService;
    this.userService = userService;
    this.coinService = coinService;
  }

  @GetMapping("/users")
  public ResponseEntity<Watchlist> getUserWatchlist(
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());

    return new ResponseEntity<>(watchlist, HttpStatus.OK);
  }

  @GetMapping("/{watchlistId}")
  public ResponseEntity<Watchlist> getWatchlistById(
    @PathVariable Long wacthlistId
  ) {
    Watchlist watchlist = watchlistService.findById(wacthlistId);

    return new ResponseEntity<>(watchlist, HttpStatus.OK);
  }

  @PatchMapping("/add/coin/{coinId}")
  public ResponseEntity<Coin> addItemToWatchlist(
  @RequestHeader("Authorization") String jwt,
  @PathVariable String coinId) {
    UserEntity user = userService.findUserByJwt(jwt);

    Coin coin = coinService.findById(coinId);
    Coin addedCoin = watchlistService.addItemToWatchlist(user, coin);

    return new ResponseEntity<>(addedCoin, HttpStatus.OK);
  }

}
