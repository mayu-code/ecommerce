package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    
}
