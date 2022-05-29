package com.bsd.skep.service;

import com.bsd.skep.entity.*;
import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.repository.AuthorRepository;
import org.springframework.stereotype.Service;

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
        return authorRepository.save(Author.builder().firstName(authorDTO.getFirstName()).lastName(authorDTO.getLastName()).build());
    }

    @Override
    public Author getAuthor(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(UUID id, String firstName, String lastName) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            if (firstName != null) {
                author.setFirstName(firstName);
            }
            if (lastName != null) {
                author.setLastName(lastName);
            }
            return authorRepository.save(author);
        }
        return null;
    }
}
