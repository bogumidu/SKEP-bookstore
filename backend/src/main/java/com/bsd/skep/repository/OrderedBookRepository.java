package com.bsd.skep.repository;

import com.bsd.skep.entity.Order;
import com.bsd.skep.entity.OrderedBook;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderedBookRepository extends CrudRepository<OrderedBook, UUID> {
}
