package com.main.ecommerce.services.impl;

import java.util.List;

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

    @Override
    public UserOrder getUserOrderById(long orderId) {
        return this.userOrderRepository.findById(orderId).get();
    }

    @Override
    public List<UserOrder> getUserOrderByUserid(long userId) {

        List<UserOrder> userOrders = this.userOrderRepository.findByUserId(userId).stream()
                .filter(order -> order.getOrderStatus().equals(OrderStatus.COMPLETED)).toList();

        return userOrders;

    }

}
