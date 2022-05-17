package com.bsd.skep.service;

import com.bsd.skep.entity.Order;
import com.bsd.skep.model.CreateOrderDTO;
import com.bsd.skep.util.OrderStatus;

import java.util.UUID;

public interface OrderService {

    Order createOrder(CreateOrderDTO createOrderDTO);

    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

}
