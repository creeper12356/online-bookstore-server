package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class CreateCartItemRequestDTO {
    private Integer bookId;
    private Integer number;
}
