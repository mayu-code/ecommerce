package com.main.ecommerce.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.ProductReposotory;
import com.main.ecommerce.repository.UserReposotory;
import com.main.ecommerce.services.UserServices;

@Service
public class UserServiceImpl implements UserServices{

    @Autowired
    private UserReposotory reposotory;

    @Autowired
    private ProductReposotory productReposotory;

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
    public Product addCart(User user, long id) {
       User user1  = reposotory.findById(user.getId()).get();
        if(user1.getMyCart().contains(id)){
            user1.getMyCart().remove(id);
        }
        else{
            user1.getMyCart().add(id);
        }
        reposotory.save(user1);
       
        return productReposotory.findById(id).get();
       
    }

    @Override
    public Product addOrder(User user, long id) {
        User user1 = reposotory.findById(user.getId()).get();
        if(user1.getMyOrders().contains(id)){
            user1.getMyOrders().remove(id);
        }else{
            user1.getMyOrders().add(id);
        }
        reposotory.save(user1);

        return productReposotory.findById(id).get();
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
    
}
