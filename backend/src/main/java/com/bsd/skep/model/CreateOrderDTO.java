package com.bsd.skep.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderDTO {
    private List<OrderedBookDTO> cart;
}
