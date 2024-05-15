package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class LoginOkResponseDTO {
    private Boolean ok;
    private String token;

    public LoginOkResponseDTO(String token) {
        this.ok = true;
        this.token = token;
    }
}
