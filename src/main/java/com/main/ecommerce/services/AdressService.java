package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.Address;

public interface AdressService {

    Address addAddressToUserWithUserId(long userId, Address address);

    void deleteAddressById(long addressId);

    List<Address> getAddressByUserId(long userId);

}