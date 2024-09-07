package com.vikram.EquinoxTrade.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.repository.CoinRepository;
import com.vikram.EquinoxTrade.service.CoinService;
import com.vikram.EquinoxTrade.urls.CoinUrl;
import com.vikram.EquinoxTrade.utils.JsonNodeUtil;

/**
 * CoinServiceImpl
 */
@Service
public class CoinServiceImpl implements CoinService {

  private CoinRepository coinRepository;

  private ObjectMapper objectMapper;

  public CoinServiceImpl(
      CoinRepository coinRepository,
      ObjectMapper objectMapper) {
    this.coinRepository = coinRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<Coin> getCoinList(int page) {
    String url = CoinUrl.coinMarketUrlWithPagination + page;

    RestTemplate restTemplate = new RestTemplate();

    try {
      HttpHeaders httpHeaders = new HttpHeaders();

      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

      List<Coin> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {
      });

      return coinList;
    } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public String getMarketChart(String coinId, int days) {
    String url = CoinUrl.getMarketChartUrl(coinId, days);

    RestTemplate restTemplate = new RestTemplate();

    try {
      HttpHeaders httpHeaders = new HttpHeaders();

      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public String getCoinDetails(String coinId) {
    String url = CoinUrl.coinUrl + coinId;
    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders httpHeaders = new HttpHeaders();
      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      Coin coin = new Coin();

      coin.setId(JsonNodeUtil.getTextSafely(jsonNode, "id"));
      coin.setName(JsonNodeUtil.getTextSafely(jsonNode, "name"));
      coin.setSymbol(JsonNodeUtil.getTextSafely(jsonNode, "symbol"));
      coin.setImage(JsonNodeUtil.getNestedTextSafely(jsonNode, "image", "large"));

      JsonNode marketData = jsonNode.get("market_data");
      if (marketData != null) {
        coin.setCurrentPrice(JsonNodeUtil.getNestedDoubleSafely(marketData, "current_price", "usd"));
        coin.setMarketCapRank(JsonNodeUtil.getIntSafely(marketData, "market_cap_rank"));
        coin.setMarketCap(JsonNodeUtil.getNestedLongSafely(marketData, "market_cap", "usd"));
        coin.setTotalVolume(JsonNodeUtil.getNestedLongSafely(marketData, "total_volume", "usd"));
        coin.setHigh24h(JsonNodeUtil.getNestedDoubleSafely(marketData, "high_24h", "usd"));
        coin.setLow24h(JsonNodeUtil.getNestedDoubleSafely(marketData, "low_24h", "usd"));
        coin.setPriceChange24h(JsonNodeUtil.getDoubleSafely(marketData, "price_change_24h"));
        coin.setPriceChangePercentage24h(JsonNodeUtil.getDoubleSafely(marketData, "price_change_percentage_24h"));
        coin.setMarketCapChange24h(JsonNodeUtil.getLongSafely(marketData, "market_cap_change_24h"));
        coin.setMarketCapChangePercentage24h(
            JsonNodeUtil.getDoubleSafely(marketData, "market_cap_change_percentage_24h"));
        coin.setTotalSupply(JsonNodeUtil.getDoubleSafely(marketData, "total_supply"));
      }

      coinRepository.save(coin);
      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
      throw new RuntimeException("Error fetching coin details: " + e.getMessage(), e);
    }
  }

  @Override
  public Coin findById(String coinId) {
    Optional<Coin> coin = coinRepository.findById(coinId);

    if (coin.isPresent()) {
      return coin.get();
    }

    throw new RuntimeException("Coin not found");
  }

  @Override
  public String searchCoin(String keyword) {
    String url = CoinUrl.searchCoinUrl + keyword;

    RestTemplate restTemplate = new RestTemplate();

    try {
      HttpHeaders httpHeaders = new HttpHeaders();

      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public String getTop50CoinsByMarketCapRank() {
    String url = CoinUrl.top50CoinsUrl;

    RestTemplate restTemplate = new RestTemplate();

    try {
      HttpHeaders httpHeaders = new HttpHeaders();

      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public String getTrendingCoins() {
    String url = CoinUrl.trendingCoinsUrl;

    RestTemplate restTemplate = new RestTemplate();

    try {
      HttpHeaders httpHeaders = new HttpHeaders();

      HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
