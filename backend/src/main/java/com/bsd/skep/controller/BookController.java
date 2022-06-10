package com.bsd.skep.controller;

import com.bsd.skep.entity.Book;
import com.bsd.skep.model.BookDTO;
import com.bsd.skep.model.BookListDTO;
import com.bsd.skep.service.BookService;
import com.bsd.skep.service.LuceneService;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;
    private final LuceneService luceneService;

    public BookController(BookService bookService, LuceneService luceneService) {
        this.bookService = bookService;
        this.luceneService = luceneService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public BookDTO book(@RequestBody BookDTO bookDTO) {
        return BookDTO.fromEntity(bookService.createBook(bookDTO));
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable UUID id) {
        return BookDTO.fromEntity(bookService.findBook(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDTO) {
        return BookDTO.fromEntity(bookService.updateBook(id, bookDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/price")
    public BookDTO updateBookPrice(@PathVariable UUID id, @RequestParam int price) {
        return BookDTO.fromEntity(bookService.updateBookPrice(id, price));
    }

    @GetMapping("/")
    public BookListDTO getBookByQuery(String query) {
        if (query == null) {
            return null;
        }
        try {
            return BookListDTO.fromList(luceneService.searchBook(query));
        } catch (QueryNodeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
