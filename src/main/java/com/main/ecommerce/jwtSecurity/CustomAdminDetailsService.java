package com.main.ecommerce.jwtSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.services.impl.AdminServiceImpl;

@Service
public class CustomAdminDetailsService implements UserDetailsService{

    @Autowired
    private AdminServiceImpl adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Admin admin = this.adminService.getByEmail(username);

        if (admin==null) {
            throw new UsernameNotFoundException("Invalid Credenitials !");
        }

        return User.builder().username(admin.getEmail()).password(admin.getPassword()).roles(admin.getRole()).authorities(admin.getAuthorities()).build();

    }
    
}
