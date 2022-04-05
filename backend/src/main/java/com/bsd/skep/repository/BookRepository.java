package com.bsd.skep.repository;

import com.bsd.skep.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
