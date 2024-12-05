package com.main.ecommerce.jwtSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.UserServiceImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = this.userService.getByEmail(username);

        if (user==null) {
            throw new UsernameNotFoundException("User Not Found !");
        }

        // List<GrantedAuthority> authorities = new ArrayList<>();

        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).authorities(user.getAuthorities()).build();

    }
    


}
