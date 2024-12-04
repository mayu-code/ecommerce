package com.main.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    
    @Query("SELECT p FROM Product p WHERE p.admin = :admin")
    Optional<List<Product>> findProductByAdmin(@Param("admin") Admin admin);

    Admin findByEmail(String email);
}
