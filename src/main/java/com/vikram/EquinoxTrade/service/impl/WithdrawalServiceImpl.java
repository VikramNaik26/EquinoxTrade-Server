package com.vikram.EquinoxTrade.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.domain.WithdrawalStatus;
import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.Withdrawal;
import com.vikram.EquinoxTrade.repository.WithdrawalRepository;
import com.vikram.EquinoxTrade.service.WithdrawalService;

/**
 * WithdrawalServiceImpl
 */
@Service
public class WithdrawalServiceImpl implements WithdrawalService {

  private WithdrawalRepository withdrawalRepository;

  public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository) {
    this.withdrawalRepository = withdrawalRepository;
  }

  @Override
  public Withdrawal requestWithdrawal(BigDecimal amount, UserEntity user) {
    Withdrawal withdrawal = new Withdrawal();

    withdrawal.setAmount(amount);
    withdrawal.setUser(user);
    withdrawal.setStatus(WithdrawalStatus.PENDING);

    return withdrawalRepository.save(withdrawal);
  }

  @Override
  public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) {
    Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);

    if(withdrawal.isEmpty()) {
      throw new RuntimeException("Withdrawal not found");
    }

    Withdrawal w = withdrawal.get();
    w.setDateTime(LocalDateTime.now());

    w.setStatus(accept? WithdrawalStatus.SUCCESS : WithdrawalStatus.DECLINED);
    return withdrawalRepository.save(w);
  }

  @Override
  public List<Withdrawal> getUsersWithdrawalHistory(UserEntity user) {
    return withdrawalRepository.findByUserId(user.getId());
  }

  @Override
  public List<Withdrawal> getAllWithdrawalRequest() {
    return withdrawalRepository.findAll();
  }

}
