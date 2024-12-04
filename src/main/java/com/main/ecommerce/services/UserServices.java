package com.main.ecommerce.services;

import com.main.ecommerce.entities.User;

public interface UserServices {

    public User registerUser(User user);
    public User getUserbyid(long id);
    
}
