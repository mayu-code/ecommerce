package com.main.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.jwtSecurity.jwtProvider;
import com.main.ecommerce.repository.AdminRepository;
import com.main.ecommerce.services.AdminServices;

@Service
public class AdminServiceImpl implements AdminServices{

    @Autowired
    private AdminRepository repository;

    @Autowired
    private UserServiceImpl userService;
   
    @Autowired
    private ProductServiceImpl productService;

    @Override
    public Optional<Admin> getAdminById(long id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<List<Product>> getAllProductsByAdmin(Admin Admin) {
        return this.repository.findProductByAdmin(Admin);
    }

    @Override
    public Product addProductWithAdminId(Product product, long adminId) {
       
        Admin admin = getAdminById(adminId).get();

        product.setAddedDate(LocalDateTime.now());
        product.setEnable(true);

        admin.getMyProducts().add(product);

        this.repository.save(admin);

        return this.productService.AddProduct(product);

    }

    @Override
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @Override
    public User updateUser(User user) {
        return this.userService.registerUser(user);
    }

    @Override
    public void deleteUser(long userId) {
        this.userService.deleteUser(userId);
    }

    @Override
    public Boolean isExist(long id) {
        return this.repository.existsById(id);
    }

    @Override
    public Admin getByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        
        Admin savedAdmin = this.repository.save(admin);

        return savedAdmin;

    }

    @Override
    public Admin getAdminByJwt(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Admin admin = this.repository.findByEmail(email);
        return admin;
    }
    
}
