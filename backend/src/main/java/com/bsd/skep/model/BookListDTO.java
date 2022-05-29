package com.bsd.skep.model;

import com.bsd.skep.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookListDTO {

    private List<BookDTO> books;

    public static BookListDTO fromList(List<Book> books) {
        return new BookListDTO(books.stream()
                        .map(BookDTO::fromEntity)
                        .collect(java.util.stream.Collectors.toList())
        );
    }
}
