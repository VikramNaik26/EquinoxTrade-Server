package com.vikram.EquinoxTrade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * AppConfig
 */
@Configuration
@EnableWebSecurity
public class AppConfig {

  private JwtTokenValidator jwtTokenValidator;
  private UserDetailsService userDetailsService;

  public AppConfig(JwtTokenValidator jwtTokenValidator, UserDetailsService userDetailsService) {
    this.jwtTokenValidator = jwtTokenValidator;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(request -> request
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .addFilterBefore(jwtTokenValidator, BasicAuthenticationFilter.class)
        .build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    return null;
  }

  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
