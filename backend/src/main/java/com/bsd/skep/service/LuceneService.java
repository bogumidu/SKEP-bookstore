package com.bsd.skep.service;

import com.bsd.skep.entity.Book;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import java.io.IOException;
import java.util.List;

public interface LuceneService {
    void createIndex();

    List<Book> searchBook(String query) throws QueryNodeException;

    void createBook(Book book) throws IOException;

    void updateBook(Book book) throws IOException;

}
