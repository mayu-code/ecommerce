package com.main.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    
    public ResponseEntity<List<Product>> getCartProducts(@PathVariable("Id") long id){
        User user = userServiceImpl.getUserbyid(id);
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductsByIds(user.getMyCart());
            return ResponseEntity.of(Optional.of(products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Product>> getOrderedProduct(@PathVariable("id") long id){
        User user = userServiceImpl.getUserbyid(id);
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductsByIds(user.getMyOrders());
            return ResponseEntity.of(Optional.of(products));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> updateUser(User user){
        try{
            return null;
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> AddCardProduct(@PathVariable("userid") long id , @PathVariable("ProductId") long ProductId){

        User user = userServiceImpl.getUserbyid(id);
        try{
            userServiceImpl.addCart(user, ProductId);
            return ResponseEntity.of(Optional.of("Product added onto the cart succesfully ! "));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> addOrderedProduct(@PathVariable("userid") long id , @PathVariable("ProductId") long ProductId){
        User user = userServiceImpl.getUserbyid(id);
        try{
            userServiceImpl.addOrder(user, ProductId);
            return ResponseEntity.of(Optional.of("Product Ordered succesfully ! "));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        try{
            return null;
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
