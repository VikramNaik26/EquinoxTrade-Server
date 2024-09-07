package com.vikram.EquinoxTrade.urls;

/**
 * CoinUrl
 */
public class CoinUrl {

  public static String coinUrl = "https://api.coingecko.com/api/v3/coins/";

  public static String coinMarketUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd";

  public static String coinMarketUrlWithPagination = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=";

  public static String searchCoinUrl = "https://api.coingecko.com/api/v3/search?query=";

  public static String top50CoinsUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=false";

  public static String trendingCoinsUrl = "https://api.coingecko.com/api/v3/search/trending";

  public static String getMarketChartUrl(String coinId, int days) {
    return "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;
  }

}
