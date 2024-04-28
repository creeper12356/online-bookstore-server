package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class GetAllBooksOkResponseDTO {
    private Integer total;
    private List<Book> items;

    public GetAllBooksOkResponseDTO(List<Book> books) {
        this.total = books.size();
        this.items = books;
    }
}
