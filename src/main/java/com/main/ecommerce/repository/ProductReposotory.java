package com.main.ecommerce.repository;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Product;

public interface ProductReposotory extends JpaRepository<Product,Long>{

   public List<Product> findByCategory(String category);
   public List<Product> findBySubcategory(String subcategory);

   List<Product> findByCategoryOrSubcategory(String category, String subcategory);

   List<Item> findByCategoryAndSubcategory(String category, String subcategory);
    
} 