package com.main.ecommerce.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.main.ecommerce.jwtSecurity.CustomAdminDetailsService;
import com.main.ecommerce.jwtSecurity.CustomUserDetailsService;
import com.main.ecommerce.jwtSecurity.JwtValidater;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

    // Admin security

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtValidater(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:5173"));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    // @Bean
    // public AuthenticationProvider adminAuthenticationProvider() {
    // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // provider.setUserDetailsService(adminDetailsService);
    // return provider;
    // }

    // user security

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtValidater(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    // @Bean
    // public AuthenticationProvider userAuthenticationProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(userDetailsService);
    //     provider.setPasswordEncoder(userPasswordEncoder());
    //     return provider;
    // }

}

// @Bean
// SecurityFilterChain filterChain (HttpSecurity http)throws Exception {
// http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
// .authorizeHttpRequests(Authorize ->
// Authorize.requestMatchers("/api/**").authenticated()
// .anyRequest().permitAll())
// .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
// .csrf(csrf ->csrf.disable())
// .cors(cors ->cors.configurationSource(corsConfigurationSource()));
// return http.build();
// }

// @Bean
// PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// private CorsConfigurationSource corsConfigurationSource() {

// return new CorsConfigurationSource() {

// @Override
// public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
// CorsConfiguration cfg = new CorsConfiguration();
// cfg.setAllowedOrigins(Arrays.asList(
// "http://localhost:5173"
// ));
// cfg.setAllowedMethods(Collections.singletonList("*"));
// cfg.setAllowCredentials(true);
// cfg.setAllowedHeaders(Collections.singletonList("*"));
// cfg.setExposedHeaders(Arrays.asList("Authorization"));
// cfg.setMaxAge(3600L);
// return cfg;
// }
// };

// }
