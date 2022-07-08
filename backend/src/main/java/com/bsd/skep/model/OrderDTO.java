package com.bsd.skep.model;

import com.bsd.skep.entity.Order;
import com.bsd.skep.util.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private List<OrderedBookDTO> cart;
    private OrderStatus orderStatus;
    private int total;
    private long creationDate;

    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(order.getId(), order.getProduct().stream().map(OrderedBookDTO::fromEntity).collect(Collectors.toList()), order.getStatus(), order.getTotal(), order.getCreationDate());
    }

}
