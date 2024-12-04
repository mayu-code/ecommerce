package com.main.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.LoginUser;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdminServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;


    //admin login



    //user registration

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user){

        if (user!=null) {

            this.userService.registerUser(user);

            return new ResponseEntity<>("User Registered Successfully !",HttpStatus.CREATED);
            
        }
        
        return new ResponseEntity<>("User Not Found !",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //user login

    @PostMapping("/user/login")
    public ResponseEntity<User> userLogin(@RequestBody LoginUser user){

        return ResponseEntity.ok(new User());

    }

}
