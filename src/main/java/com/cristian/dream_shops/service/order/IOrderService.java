package com.cristian.dream_shops.service.order;

import com.cristian.dream_shops.dto.OrderDto;
import com.cristian.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long id);
    List<OrderDto> getUserOrders(Long userId);
}
