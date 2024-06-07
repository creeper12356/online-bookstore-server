package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

@Data
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private Integer price;
    private String cover;
    private Integer sales;
    private Integer stock;
    
    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.price = book.getPrice();
        this.cover = book.getCover();
        this.sales = book.getSales();
        this.stock = book.getStock();
    }
}
