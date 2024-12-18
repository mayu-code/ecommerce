package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.entities.UserOrder;

public interface OrderStackService {

    OrderStack addOrderItem(User user, OrderItem orderItem);

    OrderStack getOrderStack(long stackId);

    OrderStack getOrderStackByUserId(long userId);

    OrderStack removeOrderItemFromCart(long orderItemId, long stackId);

    OrderStack getOrderStackByUserOrder(UserOrder userOrder);

}
