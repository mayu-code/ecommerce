package com.main.ecommerce.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.UserReposotory;
import com.main.ecommerce.services.UserServices;

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
    
}
