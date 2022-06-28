package com.bsd.skep.repository;

import com.bsd.skep.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {

    @Query("SELECT b FROM Book b WHERE b.id in :ids")
    List<Book> findByIds(List<UUID> ids);

    List<Book> findBookByGenre(String genre);

}
