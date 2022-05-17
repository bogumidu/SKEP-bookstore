package com.bsd.skep.service;

import com.bsd.skep.entity.Author;
import com.bsd.skep.entity.Book;
import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.model.BookDTO;
import com.bsd.skep.repository.AuthorRepository;
import com.bsd.skep.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthor().getId()).orElse(null);
        if (author == null) {
            return null;
        }
        return bookRepository.save(Book.builder().title(bookDTO.getTitle())
                .description(bookDTO.getDescription())
                .genre(bookDTO.getGenre())
                .creationDate(bookDTO.getCreationDate())
                .cover(bookDTO.getCover())
                .price(bookDTO.getPrice())
                .author(author).build());
    }

    @Override
    public Book findBook(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

//    @Override
//    public Book createAuthor(AuthorDTO authorDTO) {
//        return authorRepository.save(Author.builder().firstName(authorDTO.getFirstName()).lastName(authorDTO.getLastName()).build());
//    }
//
//    @Override
//    public Author findAuthor(UUID id) {
//        return authorRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public Author updateAuthor(UUID id, String firstName, String lastName) {
//        Author author = authorRepository.findById(id).orElse(null);
//        if (author != null) {
//            if (firstName != null) {
//                author.setFirstName(firstName);
//            }
//            if (lastName != null) {
//                author.setLastName(lastName);
//            }
//            return authorRepository.save(author);
//        }
//        return null;
//    }
}
