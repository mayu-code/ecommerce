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

import com.main.ecommerce.ResponseEntity.AuthResponse;
import com.main.ecommerce.ResponseEntity.DataResponse;
import com.main.ecommerce.entities.Address;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdressServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class AddressController {

    @Autowired
    private AdressServiceImpl adressService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addAddress")
    public ResponseEntity<DataResponse> addAddressToUser(@RequestHeader("Authorization") String jwt,
            @RequestBody Address address) {
        User user = userService.getUserByJwt(jwt);
        DataResponse response = new DataResponse();
        try {
            Address add = this.adressService.addAddressToUserWithUserId(user.getId(), address);

            response.setStatus(HttpStatus.CREATED);
            response.setMessage("address added !");
            response.setData(add);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("failed");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/address/delete/{addressId}")
    public ResponseEntity<AuthResponse> deleteUser(@RequestHeader("Authorization") String jwt,
            @PathVariable long addressId) {

        this.userService.getUserByJwt(jwt);
        AuthResponse response = new AuthResponse();

        if (!this.adressService.existById(addressId)) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Address Not Found !!");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            this.adressService.deleteAddressById(addressId);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Address deleted Successfully !!");

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Address Not Found !!");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @GetMapping("/address")
    public ResponseEntity<DataResponse> getAdressByUserId(@RequestHeader("Authorization") String jwt) {

        User user = this.userService.getUserByJwt(jwt);

        List<Address> addressByUserId = this.adressService.getAddressByUserId(user.getId());

        DataResponse response = new DataResponse();

        response.setStatus(HttpStatus.OK);
        response.setMessage("Address fetched Successfully");
        response.setData(addressByUserId);

        return ResponseEntity.of(Optional.of(response));

    }

}
