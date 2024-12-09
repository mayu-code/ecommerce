package com.main.ecommerce.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.OrderItemRepository;
import com.main.ecommerce.services.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    @Override
    public OrderItem addOrderiItem(User user, Product product, int quantity) {

        OrderItem orderItem = new OrderItem();

        orderItem.setUser(user);
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(quantity);

        return this.repository.save(orderItem);

    }

    @Override
    public void removeOrderItemByOrderItemId(long orderItemId) {
        this.repository.deleteById(orderItemId);

    }

    @Override
    public List<OrderItem> getOrderItemsByUserId(long userId) {
        return this.repository.findByUserId(userId);
    }

}
