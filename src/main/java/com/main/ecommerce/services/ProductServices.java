package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.Product;

public interface ProductServices {

    public List<Product> getProductbyCategery(String categery);
    
    public List<Product> getProdectbySubCategery(String SubCategery);

    public Product getProductbyId(long id);

    public Product updateProduct(Product product);

    public List<Product> allProducts ();

    public Product AddProduct(Product product);

    
}