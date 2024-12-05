package com.main.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/cartproducts")
    public ResponseEntity<List<Product>> getCartProducts(@RequestHeader("Authorization") String jwt){
        User user = userServiceImpl.getUserByJwt(jwt);
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductsByIds(user.getMyCart());
            return ResponseEntity.of(Optional.of(products));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/orderedproducts")
    public ResponseEntity<List<Product>> getOrderedProduct(@RequestHeader("Authorization") String jwt){
        User user = userServiceImpl.getUserByJwt(jwt);
        List<Product> products = new ArrayList<>();
        try{
            products = productServiceImpl.getProductsByIds(user.getMyOrders());
            return ResponseEntity.of(Optional.of(products));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt ,User user){
        User user1 = this.userServiceImpl.getUserByJwt(jwt);
        user1.setEmail(user.getEmail());
        user1.setAddress(user.getAddress());
        user1.setMobileNo(user.getMobileNo());
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setUpdateDate(LocalDateTime.now());
        try{
            this.userServiceImpl.registerUser(user1);
            return ResponseEntity.of(Optional.of(user1));
        }
        catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/addCart/{productId}")
    public ResponseEntity<String> AddCardProduct(@RequestHeader("Authorization") String jwt, @PathVariable("productId") long ProductId){

        User user = this.userServiceImpl.getUserByJwt(jwt);
        try{
            userServiceImpl.addCart(user, ProductId);
            return ResponseEntity.of(Optional.of("Product added onto the cart succesfully ! "));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ordered/{ProductId}")
    public ResponseEntity<String> addOrderedProduct(@RequestHeader("Authorization") String jwt, @PathVariable("ProductId") long ProductId){
        User user = this.userServiceImpl.getUserByJwt(jwt);
        try{
            userServiceImpl.addOrder(user, ProductId);
            return ResponseEntity.of(Optional.of("Product Ordered succesfully ! "));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String jwt){
        try{
            return ResponseEntity.of(Optional.of(this.userServiceImpl.getUserByJwt(jwt)));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
