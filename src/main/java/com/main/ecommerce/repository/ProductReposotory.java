package com.main.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Product;

public interface ProductReposotory extends JpaRepository<Product,Long>{

   public List<Product> findByCategory(String category);
   public List<Product> findBySubcategory(String subcategory);
    
} 