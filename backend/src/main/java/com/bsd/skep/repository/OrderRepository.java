package com.bsd.skep.repository;

import com.bsd.skep.entity.Book;
import com.bsd.skep.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
}
