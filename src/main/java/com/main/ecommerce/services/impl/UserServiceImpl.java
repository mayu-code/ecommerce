package com.main.ecommerce.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.jwtSecurity.jwtProvider;
import com.main.ecommerce.repository.ProductReposotory;
import com.main.ecommerce.repository.UserReposotory;
import com.main.ecommerce.services.UserServices;

@Service
public class UserServiceImpl implements UserServices{

    @Autowired
    private UserReposotory reposotory;



    @Override
    public User registerUser(User user) {
        return this.reposotory.save(user);
    }

    @Override
    public User getUserbyid(long id) {
        return this.reposotory.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return this.reposotory.save(user);
    }


    @Override
    public void deleteUser(long id) {
       this.reposotory.deleteById(id);

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users = this.reposotory.findAll();
        return users;
    }

    @Override
    public User getByEmail(String email) {
       return this.reposotory.findByEmail(email);
    }

    @Override
    public User getUserByJwt(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = this.reposotory.findByEmail(email);
        return user;
    }

    
}
