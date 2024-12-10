package com.main.ecommerce.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.OrderPay;
import com.main.ecommerce.entities.UserOrder;
import com.main.ecommerce.repository.OrderPayRepository;
import com.main.ecommerce.repository.UserOrderRepository;
import com.main.ecommerce.services.OrderPayService;
import com.main.ecommerce.status.OrderStatus;

@Service
public class OrderPayServiceImpl implements OrderPayService {

    @Autowired
    private OrderPayRepository orderPayRepository;

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Override
    public OrderPay proceedToPay(UserOrder userOrder) {

        OrderPay orderPay = new OrderPay();

        orderPay.setUserId(userOrder.getUser().getId());
        orderPay.setStackId(userOrder.getOrderStack().getStackId());
        orderPay.setTransitionId(userOrder.getTransitionId());
        orderPay.setPayStatus(OrderStatus.COMPLETED);
        userOrder.setOrderStatus(OrderStatus.COMPLETED);
        this.userOrderRepository.save(userOrder);
        orderPay.setPaidAmmount(userOrder.getTotalPaid());

        return this.orderPayRepository.save(orderPay);

    }

}
