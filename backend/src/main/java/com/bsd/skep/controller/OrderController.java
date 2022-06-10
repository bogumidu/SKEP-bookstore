package com.bsd.skep.controller;

import com.bsd.skep.model.OrderDTO;
import com.bsd.skep.service.OrderService;
import com.bsd.skep.util.OrderStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return OrderDTO.fromEntity(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable("id") UUID id) {
        return OrderDTO.fromEntity(orderService.getOrder(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDTO updateOrderStatus(@PathVariable("id") UUID id, @RequestParam OrderStatus status) {
        return OrderDTO.fromEntity(orderService.updateOrderStatus(id, status));
    }

}
