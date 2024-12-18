package com.main.ecommerce.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.Address;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.AdressReposotory;
import com.main.ecommerce.services.AdressService;

@Service
public class AdressServiceImpl implements AdressService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdressReposotory adressReposotory;

    @Override
    public Address addAddressToUserWithUserId(long userId, Address address) {

        User user = this.userService.getUserbyid(userId);

        user.getAddresses().add(address);

        address.setUser(user);
        this.userService.registerUser(user);

        return address;

    }

    @Override
    public void deleteAddressById(long addressId) {
        this.adressReposotory.deleteById(addressId);
    }

    @Override
    public List<Address> getAddressByUserId(long userId) {
        return this.adressReposotory.findByUserId(userId);
    }

    @Override
    public boolean existById(long addressId) {
        return this.adressReposotory.existsById(addressId);
    }

}
