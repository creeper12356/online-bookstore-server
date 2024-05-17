package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

@Data
public class OrderBookDTO {
    private Integer bookId;
    private String title;
    private String cover;
    private Integer number;
    private Integer price;

    public OrderBookDTO(Book book, Integer number) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.cover = book.getCover();
        this.number = number;
        this.price = book.getPrice();
    }
}
