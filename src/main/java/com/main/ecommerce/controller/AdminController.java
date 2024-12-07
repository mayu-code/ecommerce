package com.main.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdminServiceImpl;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AdminController {
    
    @Autowired
    private ProductServiceImpl productService;
    
    @Autowired
    private AdminServiceImpl adminService;
    
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String jwt,@RequestBody Product product){
        Admin admin = this.adminService.getAdminByJwt(jwt);
        this.adminService.addProductWithAdminId(product,admin.getId());
        // this.productService.AddProduct(product);

        return new ResponseEntity<>("Product Added Successfully !!",HttpStatus.CREATED);

    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestHeader("Authorization") String jwt){

        List<Product> products = new ArrayList<>();

        try{
            Admin admin = this.adminService.getAdminByJwt(jwt);
            List<Product> productsbyAdmin = this.adminService.getAllProductsByAdmin(admin).get();
            return new ResponseEntity<>(productsbyAdmin,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(products,HttpStatus.OK);
        }



    }


    @PostMapping("/products/update")
    public ResponseEntity<Product> updateProduct(@RequestHeader("Authorization") String jwt,@RequestBody Product product){
        Product updatedProduct = this.productService.AddProduct(product);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }


    @PostMapping("/users/updateUser")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String jwt,@RequestBody User user){
        user.setUpdateDate(LocalDateTime.now());
        this.adminService.updateUser(user);

        return new ResponseEntity<>("User Updated Successfully !",HttpStatus.OK);
    }


    // @GetMapping("/products/ordered")
    // public ResponseEntity<?> getOrdeProducts(){

    //     return ResponseEntity().OK;

    // }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt){
        List<User> allUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


}
