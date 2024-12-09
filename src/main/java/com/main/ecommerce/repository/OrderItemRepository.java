package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
