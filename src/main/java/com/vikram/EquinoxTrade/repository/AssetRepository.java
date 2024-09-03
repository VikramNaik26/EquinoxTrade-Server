package com.vikram.EquinoxTrade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikram.EquinoxTrade.model.Asset;

/**
 * AssetRepository
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {

  List<Asset> findByUserId(Long userId);

  Asset findByUserIdAndCoinId(Long userId, String coinId);

}
