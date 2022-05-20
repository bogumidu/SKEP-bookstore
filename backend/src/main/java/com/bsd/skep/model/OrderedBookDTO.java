package com.bsd.skep.model;

import com.bsd.skep.entity.OrderedBook;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderedBookDTO {
    private UUID id;
    private int quantity;
    private int price;
    private BookDTO book;

    public OrderedBookDTO(int quantity, int price, BookDTO book) {
        this.quantity = quantity;
        this.price = price;
        this.book = book;
    }

    public static OrderedBookDTO fromEntity(OrderedBook orderedBook) {
        return new OrderedBookDTO(orderedBook.getQuantity(), orderedBook.getPrice(), BookDTO.fromEntity(orderedBook.getProduct()));
    }

}
