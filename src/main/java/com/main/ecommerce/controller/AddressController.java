package com.main.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Address;
import com.main.ecommerce.services.impl.AdressServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AddressController {
    
    @Autowired
    private AdressServiceImpl adressService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addAddressToUser(@PathVariable long userId,@RequestBody Address address){

        Address add = this.adressService.addAddressToUserWithUserId(userId, address);

        return ResponseEntity.of(Optional.of(add));

    }
    
    @PostMapping("/delete/{addressId}")
    public ResponseEntity<?> deleteUser(@PathVariable long addressId){

        this.adressService.deleteAddressById(addressId);

        return new ResponseEntity<>("deleted !",HttpStatus.OK);

    }

}
