package com.bsd.skep.model;

import com.bsd.skep.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthorListDTO {
    private List<AuthorDTO> authors;

    public static AuthorListDTO fromList(List<Author> authors) {
        if (authors == null) {
            return null;
        }
        return new AuthorListDTO(authors.stream()
                .map(AuthorDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList())
        );
    }
}
