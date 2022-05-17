package com.bsd.skep.controller;

import com.bsd.skep.model.BookDTO;
import com.bsd.skep.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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

}
