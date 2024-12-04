package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.User;

public interface UserReposotory extends JpaRepository<User,Long> {

    
} 