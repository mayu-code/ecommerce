package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Product;

public interface ProductReposotory extends JpaRepository<Product,Long>{

    
} 