package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Book;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllBooksOkResponseDTO {
    private Integer total;
    private List<BookDTO> books;

    public GetAllBooksOkResponseDTO(Integer total, List<Book> books) {
        this.total = total;
        this.books = books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());
    }
}
