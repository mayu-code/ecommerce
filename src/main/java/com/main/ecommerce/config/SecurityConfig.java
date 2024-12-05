package com.main.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.main.ecommerce.jwtSecurity.CustomAdminDetailsService;
import com.main.ecommerce.jwtSecurity.CustomUserDetailsService;


@Configuration
public class SecurityConfig {
    
    // Admin security

    @Autowired
    private CustomAdminDetailsService adminDetailsService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception{
        http
        .securityMatcher("/admin/**")
        .authorizeHttpRequests(auth->{
            auth
            .requestMatchers("/")
            .permitAll()
            .anyRequest()
            .hasRole("Admin");
        })

        // .formLogin(l-> l.permitAll())

        // .logout(lt-> lt.permitAll())

        .authenticationProvider(adminAuthenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationProvider adminAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        return provider;
    }


    // user security

    @Bean
    public PasswordEncoder userPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception{
        http
        .securityMatcher("/user/**")
        .authorizeHttpRequests(auth->{
            auth
            .requestMatchers("/")
            .permitAll()
            .anyRequest()
            .hasRole("user");
        })

        .authenticationProvider(userAuthenticationProvider());

        return http.build();
    }   

    @Bean
    public AuthenticationProvider userAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(userPasswordEncoder());
        return provider;
    }

}
