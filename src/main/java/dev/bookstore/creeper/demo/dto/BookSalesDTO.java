package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

@Data
public class BookSalesDTO {
    private Integer id;
    private String title;
    private String cover;
    private Integer price;
    private Integer sales;
    private Integer totalPrice;

    public BookSalesDTO(Book book, Integer sales) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.cover = book.getCover();
        this.price = book.getPrice();
        this.sales = sales;
        this.totalPrice = book.getPrice() * sales;
    }
}
