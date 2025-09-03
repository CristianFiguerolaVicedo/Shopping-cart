package com.cristian.dream_shops.service.order;

import com.cristian.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long id);

    List<Order> getUserOrders(Long userId);
}
