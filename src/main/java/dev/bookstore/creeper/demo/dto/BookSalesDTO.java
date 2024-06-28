package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

@Data
public class BookSalesDTO {
    Integer id;
    String title;
    String cover;
    Integer sales;

    public BookSalesDTO(Book book, Integer sales) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.cover = book.getCover();
        this.sales = sales;
    }
}
