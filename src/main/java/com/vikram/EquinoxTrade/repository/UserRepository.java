package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  
  
}
