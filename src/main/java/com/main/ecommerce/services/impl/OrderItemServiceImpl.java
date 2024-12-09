package com.main.ecommerce.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.OrderItemRepository;
import com.main.ecommerce.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    @Override
    public OrderItem addOrderiItem(User user, Product product, int quantity) {

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(quantity);

        return this.repository.save(orderItem);

    }

    @Override
    public void removeOrderItemByOrderItemId(long orderItemId) {
        this.repository.deleteById(orderItemId);

    }


}
