package com.main.ecommerce.services;

import java.util.Optional;

import com.main.ecommerce.entities.Admin;

public interface AdminServices {

    Optional<Admin> getAdminById(long id);
}