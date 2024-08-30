package com.vikram.EquinoxTrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.EquinoxTrade.model.UserEntity;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
