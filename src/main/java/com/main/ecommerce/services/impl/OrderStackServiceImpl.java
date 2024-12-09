package com.main.ecommerce.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.OrderStackRepo;
import com.main.ecommerce.services.OrderStackService;

@Service
public class OrderStackServiceImpl implements OrderStackService {

    @Autowired
    private OrderStackRepo orderStackRepo;

    @Override
    public OrderStack addOrderItem(User user, OrderItem orderItem) {

        OrderStack orderStack = new OrderStack();
        orderStack.getMycart().add(orderItem);
        orderStack.setUser(user);
        orderStack.setTotalPrice(orderItem.getPrice() + orderStack.getTotalPrice());
        orderStackRepo.save(orderStack);

        return orderStack;

    }

    @Override
    public OrderStack getOrderStack(long stackId) {
        return this.orderStackRepo.findById(stackId).get();
    }

    @Override
    public OrderStack getOrderStackByUserId(long userId) {
        return this.orderStackRepo.findByUserId(userId);
    }

}
