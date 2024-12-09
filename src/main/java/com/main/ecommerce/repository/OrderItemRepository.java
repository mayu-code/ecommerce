package com.main.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ecommerce.entities.OrderItem;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByUserId(long id);

}
