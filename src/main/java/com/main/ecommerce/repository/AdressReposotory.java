package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Address;

public interface AdressReposotory extends JpaRepository<Address,Long> {

    
} 
