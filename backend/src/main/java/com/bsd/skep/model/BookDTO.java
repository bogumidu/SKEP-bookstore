package com.bsd.skep.model;

import com.bsd.skep.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookDTO {

    private UUID id;
    private String title;
    private String description;
    private String genre;
    private Long creationDate;
    private String cover;
    private Integer price;
    private AuthorDTO author;

    public static BookDTO fromEntity(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getDescription(), book.getGenre(), book.getCreationDate(), book.getCover(), book.getPrice(), AuthorDTO.fromEntity(book.getAuthor()));
    }
}
