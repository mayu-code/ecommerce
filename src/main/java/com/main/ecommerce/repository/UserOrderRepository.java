package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

}
