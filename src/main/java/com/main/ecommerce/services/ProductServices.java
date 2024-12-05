package com.main.ecommerce.services;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.main.ecommerce.entities.Product;

public interface ProductServices {

    public List<Product> getProductbyCategery(String categery);
    
    public List<Product> getProdectbySubCategery(String SubCategery);

    public Product getProductbyId(long id);

    public Product updateProduct(Product product);

    public List<Product> allProducts();

    public Product AddProduct(Product product);

    public List<Product> getProductsByIds(List<Long> list);

    List<Product> getProductsByCategoryOrSubcategory(String category, String subcategory);

    List<Product> getProductsByCategoryAndSubcategory(String category, String subcategory);
}