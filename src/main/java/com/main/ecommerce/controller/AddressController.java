package com.main.ecommerce.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.Address;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdressServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class AddressController {

    @Autowired
    private AdressServiceImpl adressService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddressToUser(@RequestHeader("Authorization") String jwt,@RequestBody Address address){
        User user = userService.getUserByJwt(jwt);
        Address add = this.adressService.addAddressToUserWithUserId(user.getId(), address);
        return ResponseEntity.of(Optional.of(add));

    }
    
    @PostMapping("/deleteAddress/{addressId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String jwt,@PathVariable long addressId){

  
    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAdressByUserId(@PathVariable long userId) {

        List<Address> addressByUserId = this.adressService.getAddressByUserId(userId);

        return ResponseEntity.of(Optional.of(addressByUserId));

    }


}
