package com.bsd.skep.service;

import com.bsd.skep.entity.Author;
import com.bsd.skep.entity.Book;
import com.bsd.skep.model.BookDTO;
import com.bsd.skep.repository.AuthorRepository;
import com.bsd.skep.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LuceneService luceneService;

    public BookServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, LuceneService luceneService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.luceneService = luceneService;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthor().getId()).orElse(null);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        if (bookDTO.getGenre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre is required");
        }
        if (bookDTO.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price is required");
        }
        List<Book> books = (List<Book>) bookRepository.findAll();
        for (Book book : books) {
            if (book.getTitle().equals(bookDTO.getTitle()) &&
                    book.getAuthor().getId().equals(bookDTO.getAuthor().getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already exists");
            }
        }
        Book book = bookRepository.save(Book.builder().title(bookDTO.getTitle())
                .description(bookDTO.getDescription())
                .genre(bookDTO.getGenre().toLowerCase())
                .creationDate(System.currentTimeMillis())
                .cover(bookDTO.getCover())
                .price(bookDTO.getPrice())
                .author(author).build());
        try {
            luceneService.createBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lucene: Error while indexing book");
        }
        return book;
    }

    @Override
    public Book findBook(UUID id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Books not found");
        }
        return books;
    }

    @Override
    public List<Book> findBooksByIds(List<UUID> ids) {
        List<Book> books = bookRepository.findByIds(ids);
        if (books == null || books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return books;
    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        return bookRepository.findBookByGenre(genre.toLowerCase());
    }

    @Override
    public Book updateBook(UUID id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getDescription() != null) {
            book.setDescription(bookDTO.getDescription());
        }
        if (bookDTO.getGenre() != null) {
            book.setGenre(bookDTO.getGenre());
        }
        if (bookDTO.getCover() != null) {
            book.setCover(bookDTO.getCover());
        }
        if (bookDTO.getPrice() != null) {
            book.setPrice(bookDTO.getPrice());
        }
        if (bookDTO.getAuthor() != null) {
            Author author = authorRepository.findById(bookDTO.getAuthor().getId()).orElse(null);
            if (author == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
            }
            book.setAuthor(author);
        }
        book = bookRepository.save(book);
        try {
            luceneService.updateBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lucene: Error while indexing book");
        }
        return book;
    }

    @Override
    public Book updateBookPrice(UUID id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        book.setPrice(bookDTO.getPrice());
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBookByQuery(String query) {
        if (query == null) {
            // TODO: find by newest books
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Query is null");
        }
        try {
            return luceneService.searchBook(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lucene: Error while searching book");
        }
    }
}
