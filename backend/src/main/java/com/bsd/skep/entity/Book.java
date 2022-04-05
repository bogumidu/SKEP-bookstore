package com.bsd.skep.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String genre;

    private String creationDate;

    private String cover;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(mappedBy = "book")
    private Post post;


    public Book(String title, String description, String genre, String creationDate, String cover) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.creationDate = creationDate;
        this.cover = cover;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
