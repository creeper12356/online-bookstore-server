package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class CreateCartItemOkResponseDTO {
    Boolean ok;
    String message;
    Integer id;

    public CreateCartItemOkResponseDTO(Boolean ok, String message, Integer id) {
        this.ok = ok;
        this.message = message;
        this.id = id;
    }
}
