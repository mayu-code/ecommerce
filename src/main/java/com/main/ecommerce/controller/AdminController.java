package com.main.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.main.ecommerce.ResponseEntity.AuthResponse;
import com.main.ecommerce.ResponseEntity.DataResponse;
import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdminServiceImpl;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class AdminController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addProduct")
    public ResponseEntity<AuthResponse> addProduct(@RequestHeader("Authorization") String jwt,
            @RequestBody Product product) {
        Admin admin = this.adminService.getAdminByJwt(jwt);
        this.adminService.addProductWithAdminId(product, admin.getId());
        // this.productService.AddProduct(product);

        AuthResponse response = new AuthResponse();

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Product Added Successfull");

        return ResponseEntity.of(Optional.of(response));

    }

    @GetMapping("/products")
    public ResponseEntity<DataResponse> getProducts(@RequestHeader("Authorization") String jwt) {

        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            Admin admin = this.adminService.getAdminByJwt(jwt);
            List<Product> productsbyAdmin = this.adminService.getAllProductsByAdmin(admin).get();

            response.setStatus(HttpStatus.OK);
            response.setMessage("Get Products Successfull");
            response.setData(productsbyAdmin);

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Unable To Get Products !");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        }

    }

    @PostMapping("/products/update")
    public ResponseEntity<DataResponse> updateProduct(@RequestHeader("Authorization") String jwt,
            @RequestBody Product product) {
        Product updatedProduct = this.productService.AddProduct(product);

        DataResponse response = new DataResponse();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Product updated Successfully");
        response.setData(updatedProduct);

        return ResponseEntity.of(Optional.of(response));
    }

    @PostMapping("/users/updateUser")
    public ResponseEntity<DataResponse> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) {
        user.setUpdateDate(LocalDateTime.now());
        User updatedUser = this.adminService.updateUser(user);

        DataResponse response = new DataResponse();

        response.setStatus(HttpStatus.OK);
        response.setMessage("User Updated Successfully !");
        response.setData(updatedUser);

        return ResponseEntity.of(Optional.of(response));
    }

    // @GetMapping("/products/ordered")
    // public ResponseEntity<?> getOrdeProducts(){

    // return ResponseEntity().OK;

    // }

    @GetMapping("/allUsers")
    public ResponseEntity<DataResponse> getAllUsers(@RequestHeader("Authorization") String jwt) {
        List<User> allUsers = this.userService.getAllUsers();

        DataResponse response = new DataResponse();

        response.setStatus(HttpStatus.OK);
        response.setMessage("Fetch all users successful");
        response.setData(allUsers);

        return ResponseEntity.of(Optional.of(response));
    }

}
