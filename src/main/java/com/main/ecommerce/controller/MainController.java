package com.main.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/Home")
public class MainController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    
    public ResponseEntity<List<Product>> allProducts1(){
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.allProducts();
            return ResponseEntity.of(Optional.of(products));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("getProductByCategery/{categery}")
    public ResponseEntity<List<Product>> allProductsbyCategery(@PathVariable("categery") String categery){
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductbyCategery(categery);
            return ResponseEntity.of(Optional.of(products));

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("getProductBySubCategery/{subcategery}")
    public ResponseEntity<List<Product>> allProductsbySubCategery(@PathVariable("Subcategery") String subcategery){
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductbyCategery(subcategery);
            return ResponseEntity.of(Optional.of(products));

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
