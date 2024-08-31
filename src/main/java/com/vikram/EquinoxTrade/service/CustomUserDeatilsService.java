package com.vikram.EquinoxTrade.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vikram.EquinoxTrade.model.UserEntity;
import com.vikram.EquinoxTrade.model.UserPricipal;
import com.vikram.EquinoxTrade.repository.UserRepository;

/**
 * CustomUserDeatilsService
 */
@Service
public class CustomUserDeatilsService implements UserDetailsService {
  private UserRepository userRepository;

  public CustomUserDeatilsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + email);
    }
    return new UserPricipal(user);
  }

}
