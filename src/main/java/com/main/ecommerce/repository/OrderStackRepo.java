package com.main.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.OrderStack;

public interface OrderStackRepo extends JpaRepository<OrderStack, Long> {

    List<OrderStack> findByUserId(long userId);
}
