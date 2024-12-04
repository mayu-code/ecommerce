package com.main.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;

public interface AdminServices {

    Optional<Admin> getAdminById(long id);

    Optional<List<Product>> getAllProductsByAdmin(Admin Admin);
}