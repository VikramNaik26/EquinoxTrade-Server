package com.vikram.EquinoxTrade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.model.Asset;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.repository.AssetRepository;
import com.vikram.EquinoxTrade.service.AssetService;

/**
 * AssetServiceImpl
 */
@Service
public class AssetServiceImpl implements AssetService {

  private AssetRepository assetRepository;

  public AssetServiceImpl(AssetRepository assetRepository) {
    this.assetRepository = assetRepository;
  }

  @Override
  public Asset createAsset(UserEntity user, Coin coin, double quantity) {
    Asset asset = new Asset();

    asset.setBuyPrice(BigDecimal.valueOf(coin.getCurrentPrice()));
    asset.setCoin(coin);
    asset.setQuantity(quantity);
    asset.setUser(user);

    return assetRepository.save(asset);
  }

  @Override
  public Asset getAssetById(Long id) {
    return assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
  }

  @Override
  public Asset getAssetByUserIdAndId(Long userId, Long id) {
    // TODO Auto-generated method stuj
    throw new UnsupportedOperationException("Unimplemented method 'getAssetByUserIdAndId'");
  }

  @Override
  public List<Asset> getUserAssets(Long userId) {
    return assetRepository.findByUserId(userId);
  }

  @Override
  public Asset updateAsset(Long assetId, double quantity) {
    Asset oldAsset = assetRepository.findById(assetId).orElseThrow(() -> new RuntimeException("Asset not found"));

    oldAsset.setQuantity(quantity + oldAsset.getQuantity());
    return assetRepository.save(oldAsset);
  }

  @Override
  public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
    return assetRepository.findByUserIdAndCoinId(userId, coinId);
  }

  @Override
  public void deleteAsset(Long assetId) {
    assetRepository.deleteById(assetId);
  }

}
