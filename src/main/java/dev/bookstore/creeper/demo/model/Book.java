package dev.bookstore.creeper.demo.model;


import java.util.List;

import dev.bookstore.creeper.demo.dto.UpdateBookInfoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "cover")
    private String cover;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(cascade = CascadeType.ALL)    
    private List<Comment> comments;

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

    public void updateInfo(UpdateBookInfoDTO dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.cover = dto.getCover();
        this.sales = dto.getSales();
        this.stock = dto.getStock();
    }
}
