package com.vikram.EquinoxTrade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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

  public AppConfig(JwtTokenValidator jwtTokenValidator) {
    this.jwtTokenValidator = jwtTokenValidator;
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

}
