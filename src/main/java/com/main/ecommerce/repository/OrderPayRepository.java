package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.ecommerce.entities.OrderPay;

@Repository
public interface OrderPayRepository extends JpaRepository<OrderPay, Long> {

}
