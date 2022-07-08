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
public class OrderListDTO {
    private List<OrderDTO> orders;

    public static OrderListDTO fromList(List<Order> orders) {
        return new OrderListDTO(orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList()));
    }

}
