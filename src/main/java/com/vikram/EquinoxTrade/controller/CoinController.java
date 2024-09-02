package com.vikram.EquinoxTrade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.service.CoinService;

/**
 * CoinController
 */
@RestController
@RequestMapping("/coins")
public class CoinController {

  private CoinService coinService;

  private ObjectMapper objectMapper;

  public CoinController(CoinService coinService, ObjectMapper objectMapper) {
    this.coinService = coinService;
    this.objectMapper = objectMapper;
  }

  @GetMapping
  public ResponseEntity<List<Coin>> getCoinList(@RequestParam int page) {
    List<Coin> coinList = coinService.getCoinList(page);
    return new ResponseEntity<>(coinList, HttpStatus.OK);
  }

  @GetMapping("/{coinId}/chart")
  public ResponseEntity<JsonNode> getMarketChart(
      @PathVariable String coinId,
      @RequestParam int days)
      throws JsonMappingException, JsonProcessingException {
    String chart = coinService.getMarketChart(coinId, days);
    JsonNode jsonNode = objectMapper.readTree(chart);

    return new ResponseEntity<>(jsonNode, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword)
      throws JsonMappingException, JsonProcessingException {
    String chart = coinService.searchCoin(keyword);
    JsonNode jsonNode = objectMapper.readTree(chart);

    return new ResponseEntity<>(jsonNode, HttpStatus.OK);
  }

  @GetMapping("/top50")
  public ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank()
      throws JsonMappingException, JsonProcessingException {
    String chart = coinService.getTop50CoinsByMarketCapRank();
    JsonNode jsonNode = objectMapper.readTree(chart);

    return new ResponseEntity<>(jsonNode, HttpStatus.OK);
  }

  @GetMapping("/trading")
  public ResponseEntity<JsonNode> getTradingCoins()
      throws JsonMappingException, JsonProcessingException {
    String chart = coinService.getTradingCoins();
    JsonNode jsonNode = objectMapper.readTree(chart);

    return new ResponseEntity<>(jsonNode, HttpStatus.OK);
  }
}
