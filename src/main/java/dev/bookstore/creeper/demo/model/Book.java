package dev.bookstore.creeper.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    private Integer id;

    private String title;
    private String author;
    private String description;
    private Integer price;
    private String cover;
    private Integer sales;

    public Book() {
        // not used
    }
    public Book(Integer id, String title, String author, String description, Integer price, String cover, Integer sales) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.cover = cover;
        this.sales = sales;
    }
}
