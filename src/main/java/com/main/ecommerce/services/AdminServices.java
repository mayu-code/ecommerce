package com.main.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.UserServiceImpl;

public interface AdminServices {

    Boolean isExist(long id);

    Optional<Admin> getAdminById(long id);

    Optional<List<Product>> getAllProductsByAdmin(Admin Admin);

    Product addProductWithAdminId(Product product, long adminId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(long userId);




}