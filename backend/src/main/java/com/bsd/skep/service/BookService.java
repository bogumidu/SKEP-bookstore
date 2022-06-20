package com.bsd.skep.service;


import com.bsd.skep.entity.Author;
import com.bsd.skep.entity.Book;
import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.model.BookDTO;

import java.util.List;
import java.util.UUID;

public interface BookService {

     Book createBook(BookDTO bookDTO);

     Book findBook(UUID id);

     List<Book> findBooksByIds(List<UUID> ids);

     Book updateBook(UUID id, BookDTO bookDTO);

     Book updateBookPrice(UUID id, BookDTO bookDTO);

     List<Book> findBookByQuery(String query);

}
