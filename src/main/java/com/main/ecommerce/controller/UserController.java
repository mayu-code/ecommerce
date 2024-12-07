package com.main.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.ResponseEntity.DataResponse;
import com.main.ecommerce.entities.Address;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class UserController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/cartproducts")
    public ResponseEntity<DataResponse> getCartProducts(@RequestHeader("Authorization") String jwt) {
        User user = userServiceImpl.getUserByJwt(jwt);
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.getProductsByIds(user.getMyCart());
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("/orderedproducts")
    public ResponseEntity<DataResponse> getOrderedProduct(@RequestHeader("Authorization") String jwt) {
        User user = userServiceImpl.getUserByJwt(jwt);
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();
        try {
            products = productServiceImpl.getProductsByIds(user.getMyOrders());
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

    // @PostMapping("/updateUser")
    // public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String
    // jwt ,User user){
    // User user1 = this.userServiceImpl.getUserByJwt(jwt);
    // user1.setEmail(user.getEmail());
    // user1.setAddress(user.getAddress());
    // user1.setMobileNo(user.getMobileNo());
    // user1.setName(user.getName());
    // user1.setPassword(user.getPassword());
    // user1.setUpdateDate(LocalDateTime.now());
    // try{
    // this.userServiceImpl.registerUser(user1);
    // return ResponseEntity.of(Optional.of(user1));
    // }
    // catch(Exception e){
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    // }
    // }
    @PostMapping("/updateUser")
    public ResponseEntity<DataResponse> updateUser(@RequestHeader("Authorization") String jwt,
            @RequestBody User updatedUser) {

        DataResponse response = new DataResponse();

        try {
            // Retrieve the existing user using JWT
            User existingUser = this.userServiceImpl.getUserByJwt(jwt);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Update basic user fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMobileNo(updatedUser.getMobileNo());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUpdateDate(LocalDateTime.now());

            // Save updated user and new address
            User savedUser = this.userServiceImpl.updateUser(existingUser);
            response.setStatus(HttpStatus.OK);
            response.setMessage("User Updated Successfully");
            response.setData(savedUser);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("User Updated failed");

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @PostMapping("/addCart/{productId}")
    public ResponseEntity<DataResponse> AddCardProduct(@RequestHeader("Authorization") String jwt,
            @PathVariable("productId") long ProductId) {

        User user = this.userServiceImpl.getUserByJwt(jwt);

        DataResponse response = new DataResponse();

        try {
            userServiceImpl.addCart(user, ProductId);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Product added onto the cart succesfully !");

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Product added onto the cart failed !");

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @PostMapping("/removeCart/{productId}")
    public ResponseEntity<String> removeCardProduct(@RequestHeader("Authorization") String jwt,
            @PathVariable("productId") long ProductId) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        try {
            userServiceImpl.removeCart(user, ProductId);
            return ResponseEntity.of(Optional.of("Product remove from the cart the cart succesfully ! "));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/ordered/{ProductId}")
    public ResponseEntity<String> addOrderedProduct(@RequestHeader("Authorization") String jwt,
            @PathVariable("ProductId") long ProductId) {
        User user = this.userServiceImpl.getUserByJwt(jwt);
        try {
            userServiceImpl.addOrder(user, ProductId);
            return ResponseEntity.of(Optional.of("Product Ordered succesfully ! "));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.of(Optional.of(this.userServiceImpl.getUserByJwt(jwt)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
