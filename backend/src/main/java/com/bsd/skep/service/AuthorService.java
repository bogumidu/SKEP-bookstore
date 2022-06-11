package com.bsd.skep.service;


import com.bsd.skep.entity.Author;
import com.bsd.skep.model.AuthorDTO;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

     Author createAuthor(AuthorDTO authorDTO);

     Author getAuthor(UUID id);

     List<Author> getAllAuthors();

     Author updateAuthor(UUID id, AuthorDTO authorDTO);

}
