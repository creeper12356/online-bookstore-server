package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class GeneralResponseDTO {
    private Boolean ok;
    private String message;
    public GeneralResponseDTO(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }
}
