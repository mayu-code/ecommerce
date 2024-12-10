package com.main.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.Address;

public interface AdressReposotory extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long id);

}
