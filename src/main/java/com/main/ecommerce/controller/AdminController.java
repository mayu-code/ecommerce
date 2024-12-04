package com.main.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AdminController {
    
    @Autowired
    private ProductServiceImpl productService;
    
    @Autowired
    private AdminServiceImpl adminService;
    
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addProduct/{adminId}")
    public ResponseEntity<?> addProduct(@RequestBody Product product,@PathVariable long adminId){

        this.adminService.addProductWithAdminId(product, adminId);

        return new ResponseEntity<>("Product Added Successfully !!",HttpStatus.CREATED);

    }


    @GetMapping("/products/{adminId}")
    public ResponseEntity<List<Product>> getProducts(@PathVariable long adminId){

        List<Product> products = new ArrayList<>();

        if(this.adminService.isExist(adminId)){

            Admin admin = this.adminService.getAdminById(adminId).get();
    
            List<Product> productsbyAdmin = this.adminService.getAllProductsByAdmin(admin).get();

            return new ResponseEntity<>(productsbyAdmin,HttpStatus.OK);

        }

        return new ResponseEntity<>(products,HttpStatus.OK);

    }


    @PostMapping("/products/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product updatedProduct = this.productService.AddProduct(product);

        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }


    @PostMapping("/users/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        this.adminService.updateUser(user);

        return new ResponseEntity<>("User Updated Successfully !",HttpStatus.OK);
    }


    // @GetMapping("/products/ordered")
    // public ResponseEntity<?> getOrdeProducts(){

    //     return ResponseEntity().OK;

    // }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


}
