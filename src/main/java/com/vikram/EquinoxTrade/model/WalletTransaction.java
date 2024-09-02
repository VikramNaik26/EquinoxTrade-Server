package com.vikram.EquinoxTrade.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vikram.EquinoxTrade.domain.WalletTransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * WalletTransaction
 */
@Entity
public class WalletTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long Id;

  @ManyToOne
  private Wallet wallet;

  private WalletTransactionType walletTransactionType;

  private LocalDate date;

  private String transferId;

  private String purpose;

  private BigDecimal amount;

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public WalletTransactionType getWalletTransactionType() {
    return walletTransactionType;
  }

  public void setWalletTransactionType(WalletTransactionType walletTransactionType) {
    this.walletTransactionType = walletTransactionType;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getTransferId() {
    return transferId;
  }

  public void setTransferId(String transferId) {
    this.transferId = transferId;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
