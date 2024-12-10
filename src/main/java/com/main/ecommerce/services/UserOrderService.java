package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.UserOrder;

public interface UserOrderService {

    UserOrder creatOrder(UserOrder userOrder, long stackId);

    UserOrder getUserOrderById(long orderId);

    List<UserOrder> getUserOrderByUserid(long userId);

}
