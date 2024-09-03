package com.vikram.EquinoxTrade.service;

import java.util.List;

import com.vikram.EquinoxTrade.model.Asset;
import com.vikram.EquinoxTrade.model.Coin;
import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * AssetService
 */
public interface AssetService {

  public Asset createAsset(UserEntity user, Coin coin, double quantity);

  public Asset getAssetById(Long id);

  public Asset getAssetByUserIdAndId(Long userId, Long id);

  public List<Asset> getUserAssets(Long userId);

  public Asset updateAsset(Long assetId, double quantity);

  public Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  public void deleteAsset(Long assetId);
  
}
