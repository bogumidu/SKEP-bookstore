package com.bsd.skep.controller;

import com.bsd.skep.model.ApiResponse;
import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.model.AuthorListDTO;
import com.bsd.skep.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ApiResponse<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return new ApiResponse<>(AuthorDTO.fromEntity(authorService.createAuthor(authorDTO)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<AuthorDTO> getAuthor(@PathVariable("id") UUID id) {
        return new ApiResponse<>(AuthorDTO.fromEntity(authorService.getAuthor(id)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ApiResponse<AuthorListDTO> getAllAuthors() {
        return new ApiResponse<>(AuthorListDTO.fromList(authorService.getAllAuthors()));
    }

}
