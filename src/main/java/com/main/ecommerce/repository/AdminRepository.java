package com.main.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    
    Optional<List<Product>> findProductsByAdmin(Admin admin);
}
