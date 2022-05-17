package com.bsd.skep.model;

import com.bsd.skep.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AuthorDTO {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    public AuthorDTO(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static AuthorDTO fromEntity(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName());
    }
}
