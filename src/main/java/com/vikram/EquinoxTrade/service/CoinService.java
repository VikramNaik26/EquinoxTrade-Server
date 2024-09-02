package com.vikram.EquinoxTrade.service;

import java.util.List;

import com.vikram.EquinoxTrade.model.Coin;

/**
 * CoinService
 */
public interface CoinService {

  List<Coin> getCoinList(int page);

  String getMarketChart(String coinId, int days);

  String getCoinDetails(String coinId);

  Coin findById(String coinId);

  String searchCoin(String keyword);

  String getTop50CoinsByMarketCapRank();

  String getTradingCoins();
  
}
