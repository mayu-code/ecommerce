package com.main.ecommerce.services;

import java.util.List;

import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;

public interface OrderItemService {

    OrderItem addOrderiItem(Product product, int quantity);

    void removeOrderItemByOrderItemId(long orderItemId);

}
