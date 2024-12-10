package com.main.ecommerce.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.UserOrder;
import com.main.ecommerce.repository.OrderStackRepo;
import com.main.ecommerce.repository.UserOrderRepository;
import com.main.ecommerce.services.UserOrderService;
import com.main.ecommerce.status.OrderStatus;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private OrderStackServiceImpl orderStackServiceImpl;

    @Autowired
    private OrderStackRepo orderStackRepo;

    @Override
    public UserOrder creatOrder(UserOrder userOrder, long stackId) {

        OrderStack orderStack = this.orderStackServiceImpl.getOrderStack(stackId);

        userOrder.setOrderStack(orderStack);
        userOrder.setUser(orderStack.getUser());

        orderStack.setStatus(OrderStatus.COMPLETED);

        this.orderStackRepo.save(orderStack);

        orderStack.setUserOrder(userOrder);

        return this.userOrderRepository.save(userOrder);

    }

}
