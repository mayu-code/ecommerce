package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Adrress;

public interface AdressReposotory extends JpaRepository<Adrress,Long> {

    
} 
