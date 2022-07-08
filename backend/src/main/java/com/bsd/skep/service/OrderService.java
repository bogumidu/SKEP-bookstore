package com.bsd.skep.service;

import com.bsd.skep.entity.Order;
import com.bsd.skep.model.OrderDTO;
import com.bsd.skep.util.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    Order getOrder(UUID orderId);

    List<Order> getAllOrders();

    List<Order> findOrdersByUserId(UUID userId);

}
