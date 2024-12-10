package com.main.ecommerce.services;

import com.main.ecommerce.entities.OrderPay;
import com.main.ecommerce.entities.UserOrder;

public interface OrderPayService {

    OrderPay proceedToPay(UserOrder userOrder);
}
