package com.main.ecommerce.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.repository.OrderItemRepository;
import com.main.ecommerce.repository.OrderStackRepo;
import com.main.ecommerce.services.OrderStackService;
import com.main.ecommerce.status.OrderStatus;

@Service
public class OrderStackServiceImpl implements OrderStackService {

    @Autowired
    private OrderStackRepo orderStackRepo;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderStack addOrderItem(User user, OrderItem orderItem) {

        List<OrderStack> orderStack = orderStackRepo.findByUserId(user.getId());
        for (OrderStack o : orderStack) {
            if (o != null && o.getStatus().equals(OrderStatus.PENDING)) {
                o.getMycart().add(orderItem);
                o.setTotalPrice(orderItem.getPrice() + o.getTotalPrice());
                orderItem.setStack(o);
                this.orderItemRepository.save(orderItem);
                orderStackRepo.save(o);
                return o;
            }

        }
        OrderStack orderStack1 = new OrderStack();
        orderStack1.setUser(user);
        orderStack1.getMycart().add(orderItem);
        orderStack1.setTotalPrice(orderItem.getPrice());
        orderItem.setStack(orderStack1);
        return orderStackRepo.save(orderStack1);

    }

    @Override
    public OrderStack getOrderStack(long stackId) {
        return this.orderStackRepo.findById(stackId).get();
    }

    @Override
    public OrderStack getOrderStackByUserId(long userId) {
        List<OrderStack> orders = this.orderStackRepo.findByUserId(userId);
        return orders.stream().filter(o -> o.getStatus().equals(OrderStatus.PENDING)).findFirst().get();
    }

    @Override
    public OrderStack removeOrderItemFromCart(long orderItemId, long stackId) {

        OrderStack orderStack = this.orderStackRepo.findById(stackId).get();

        OrderItem orderItem = orderStack.getMycart().stream().filter(item -> item.getItemId() == orderItemId)
                .findFirst().get();

        this.orderItemRepository.delete(orderItem);

        orderStack.setTotalPrice(orderStack.getTotalPrice() - orderItem.getPrice());

        return this.orderStackRepo.save(orderStack);

    }

}
