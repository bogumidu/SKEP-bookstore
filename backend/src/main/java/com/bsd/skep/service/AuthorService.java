package com.bsd.skep.service;


import com.bsd.skep.entity.Author;
import com.bsd.skep.model.AuthorDTO;

import java.util.UUID;

public interface AuthorService {

     Author createAuthor(AuthorDTO authorDTO);

     Author findAuthor(UUID id);

     Author updateAuthor(UUID id, String firstName, String lastName);

}
