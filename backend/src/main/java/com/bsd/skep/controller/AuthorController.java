package com.bsd.skep.controller;

import com.bsd.skep.model.AuthorDTO;
import com.bsd.skep.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        return AuthorDTO.fromEntity(authorService.createAuthor(authorDTO));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping()
//    public List<AuthorDTO> getAllAuthor() {
//        return AuthorDTO.fromEntity(authorService.getAllAuthor());
//    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable("id") UUID id) {
        return AuthorDTO.fromEntity(authorService.findAuthor(id));
    }

}
