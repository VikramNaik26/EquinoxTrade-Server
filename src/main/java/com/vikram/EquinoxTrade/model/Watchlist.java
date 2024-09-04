package com.vikram.EquinoxTrade.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

/**
 * Watchlist
 */
@Entity
public class Watchlist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private UserEntity user;

  @ManyToMany
  private List<Coin> coins = new ArrayList<Coin>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public List<Coin> getCoins() {
    return coins;
  }

  public void setCoins(List<Coin> coins) {
    this.coins = coins;
  }

}
