package com.main.ecommerce.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.repository.ProductReposotory;
import com.main.ecommerce.services.ProductServices;

public class ProductServiceImpl implements ProductServices{

    @Autowired
    ProductReposotory productReposotory;

    @Override
    public List<Product> getProductbyCategery(String categery) {
        List<Product> products = productReposotory.findByCategory(categery);
        return products;
    }

    @Override
    public List<Product> getProdectbySubCategery(String SubCategery) {
        List<Product> products = productReposotory.findBySubcategory(SubCategery);
        return products;  
    }

    @Override
    public Product getProductbyId(long id) {
        Product product = productReposotory.findById(id).get();
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> allProducts() {
       List<Product> products = productReposotory.findAll();
       return products;   
    }

    @Override
    public Product AddProduct(Product product) {
        Product product2 = productReposotory.save(product);
        return product2;   
    }

    @Override
    public List<Product> getProductsByIds(List<Long> list) {
        List<Product> products = new ArrayList<>();
        for(long id : list){
            products.add(productReposotory.findById(id).get());
        }
        return products;
    }
    
}
