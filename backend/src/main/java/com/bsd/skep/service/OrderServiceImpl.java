package com.bsd.skep.service;

import com.bsd.skep.entity.Book;
import com.bsd.skep.entity.Order;
import com.bsd.skep.entity.OrderedBook;
import com.bsd.skep.entity.User;
import com.bsd.skep.model.OrderDTO;
import com.bsd.skep.model.OrderedBookDTO;
import com.bsd.skep.repository.BookRepository;
import com.bsd.skep.repository.OrderRepository;
import com.bsd.skep.repository.OrderedBookRepository;
import com.bsd.skep.util.OrderStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;
    private final OrderedBookRepository orderedBookRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(BookRepository bookRepository, OrderedBookRepository orderedBookRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.orderedBookRepository = orderedBookRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        List<Book> books = bookRepository.findByIds(orderDTO.getCart()
                .stream().map(OrderedBookDTO::getBookId).collect(Collectors.toList()));
        if (books.size() != orderDTO.getCart().size()) {
            throw new IllegalArgumentException("Invalid book id");
        }
        List<OrderedBook> orderedBooks = books.stream().map(book -> orderedBookRepository.save(OrderedBook.builder()
                .quantity(orderDTO.getCart().stream()
                        .filter(orderedBookDTO -> orderedBookDTO.getBook().getId().equals(book.getId())).findFirst().orElseThrow().getQuantity())
                .price(book.getPrice())
                .product(book)
                .build())).collect(Collectors.toList());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return orderRepository.save(Order.builder()
                .creationDate(System.currentTimeMillis())
                .status(OrderStatus.PENDING)
                .product(orderedBooks)
                .user(user)
                .total(orderedBooks.stream().mapToInt(orderedBook -> orderedBook.getPrice() * orderedBook.getQuantity()).sum())
                .build());
    }

    @Override
    public Order updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}
