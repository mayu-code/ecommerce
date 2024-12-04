package com.main.ecommerce.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.repository.AdminRepository;
import com.main.ecommerce.services.AdminServices;

public class AdminServiceImpl implements AdminServices{

    @Autowired
    private AdminRepository repository;

    @Override
    public Optional<Admin> getAdminById(long id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<List<Product>> getAllProductsByAdmin(Admin Admin) {
        return this.repository.findProductsByAdmin(Admin);
    }

    @Override
    public Admin addProductWithAdmin(Product product, long adminId) {
       
        Admin admin = getAdminById(adminId).get();

        admin.getMyProducts().add(product);

        return this.repository.save(admin);

        

    }
    
}
