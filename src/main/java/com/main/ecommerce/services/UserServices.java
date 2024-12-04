package com.main.ecommerce.services;

import com.main.ecommerce.entities.User;

public interface UserServices {

    public User registerUser(User user);
    public User getUserbyid(long id);
    public User updateUser(User user);
    public String addCart(User user , long id);
    public String addOrder(User user , long id);
    
}
