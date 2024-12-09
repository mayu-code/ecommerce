package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.User;

public interface OrderStackService {

    OrderStack addOrderItem(User user, OrderItem orderItem);

    OrderStack getOrderStack(long stackId);

}
