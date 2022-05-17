package com.bsd.skep.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderedBookDTO {
    private UUID id;
    private int quantity;
}
