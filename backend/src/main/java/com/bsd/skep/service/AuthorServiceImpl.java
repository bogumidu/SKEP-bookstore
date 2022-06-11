package com.bsd.skep.service;

import com.bsd.skep.entity.*;
import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(AuthorDTO authorDTO) {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        for (Author author : authors) {
            if (author.getFirstName().equals(authorDTO.getFirstName()) &&
                    author.getLastName().equals(authorDTO.getLastName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Author already exists");
            }
        }
        return authorRepository.save(Author.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .build());
    }

    @Override
    public Author getAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        return author;
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        if (authorDTO.getFirstName() != null) {
            author.setFirstName(authorDTO.getFirstName());
        }
        if (authorDTO.getLastName() != null) {
            author.setLastName(authorDTO.getLastName());
        }
        return authorRepository.save(author);

    }
}
