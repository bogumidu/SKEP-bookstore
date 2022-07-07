package com.bsd.skep.controller;

import com.bsd.skep.entity.Book;
import com.bsd.skep.model.ApiResponse;
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
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<BookDTO> book(@RequestBody BookDTO bookDTO) {
        return new ApiResponse<>(BookDTO.fromEntity(bookService.createBook(bookDTO)));
    }

    @GetMapping("/all")
    public ApiResponse<BookListDTO> getBooks() {
        return new ApiResponse<>(BookListDTO.fromList(bookService.getAllBooks()));
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> getBook(@PathVariable UUID id) {
        return new ApiResponse<>(BookDTO.fromEntity(bookService.findBook(id)));
    }

    @GetMapping("list/{ids}")
    public ApiResponse<BookListDTO> getBooks(@PathVariable List<UUID> ids) {
        return new ApiResponse<>(BookListDTO.fromList(bookService.findBooksByIds(ids)));
    }

    @GetMapping("/genre/{genre}")
    public ApiResponse<BookListDTO> getBooksByGenre(@PathVariable String genre) {
        return new ApiResponse<>(BookListDTO.fromList(bookService.findBooksByGenre(genre)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDTO) {
        return new ApiResponse<>(BookDTO.fromEntity(bookService.updateBook(id, bookDTO)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ApiResponse<BookDTO> updateBookPrice(@PathVariable UUID id, @RequestBody BookDTO bookDTO) {
        return new ApiResponse<>(BookDTO.fromEntity(bookService.updateBookPrice(id, bookDTO)));
    }

    @GetMapping
    public ApiResponse<BookListDTO> getBookByQuery(String query) {
        return new ApiResponse<>(BookListDTO.fromList(bookService.findBookByQuery(query)));
    }

}
