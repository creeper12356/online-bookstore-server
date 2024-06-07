package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class CreateBookOkResponseDTO {
    Boolean ok;
    String message;
    Integer id;

    public CreateBookOkResponseDTO(Boolean ok, String message, Integer id) {
        this.ok = ok;
        this.message = message;
        this.id = id;
    }
}
