package com.main.ecommerce.services;

import java.util.List;


import com.main.ecommerce.entities.User;

public interface UserServices {

    public User registerUser(User user);
    public User getUserbyid(long id);
    public User updateUser(User user);
    public void deleteUser(long id);
    public List<User> getAllUsers();
    User getByEmail(String email);
    User getUserByJwt(String jwt);
}
