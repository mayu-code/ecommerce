package com.main.ecommerce.services;

import com.main.ecommerce.entities.UserOrder;

public interface UserOrderService {

    UserOrder creatOrder(UserOrder userOrder, long stackId);
}
