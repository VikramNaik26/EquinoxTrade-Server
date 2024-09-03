package com.vikram.EquinoxTrade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.EquinoxTrade.model.Asset;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.service.AssetService;
import com.vikram.EquinoxTrade.service.UserService;

/**
 * AssetController
 */
@RestController
@RequestMapping("/api/asset")
public class AssetController {

  private AssetService assetService;
  private UserService userService;

  public AssetController(AssetService assetService, UserService userService) {
    this.assetService = assetService;
    this.userService = userService;
  }

  @GetMapping("/{assetId}")
  public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) {
    Asset asset = assetService.getAssetById(assetId);
    return new ResponseEntity<>(asset, HttpStatus.OK);
  }

  @GetMapping("/{assetId}")
  public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
      @PathVariable String coinId,
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);

    return new ResponseEntity<>(asset, HttpStatus.OK);
  }

  @GetMapping("/{assetId}")
  public ResponseEntity<List<Asset>> getAssetsForUser(
      @RequestHeader("Authorization") String jwt) {
    UserEntity user = userService.findUserByJwt(jwt);
    List<Asset> asset = assetService.getUserAssets(user.getId());

    return new ResponseEntity<>(asset, HttpStatus.OK);
  }

}
