package com.bsd.skep.controller;

import com.bsd.skep.model.ApiResponse;
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
    public ApiResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return new ApiResponse<>(OrderDTO.fromEntity(orderService.createOrder(orderDTO)));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderDTO> getOrder(@PathVariable("id") UUID id) {
        return new ApiResponse<>(OrderDTO.fromEntity(orderService.getOrder(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<OrderDTO> updateOrderStatus(@PathVariable("id") UUID id, @RequestParam OrderStatus status) {
        return new ApiResponse<>(OrderDTO.fromEntity(orderService.updateOrderStatus(id, status)));
    }

}
