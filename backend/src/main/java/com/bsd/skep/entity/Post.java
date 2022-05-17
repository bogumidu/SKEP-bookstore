package com.bsd.skep.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "post")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String price;

    private int count;

    private int bought;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;


    public Post(String title, String price, int count, int bought, String description) {
        this.title = title;
        this.price = price;
        this.count = count;
        this.bought = bought;
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
