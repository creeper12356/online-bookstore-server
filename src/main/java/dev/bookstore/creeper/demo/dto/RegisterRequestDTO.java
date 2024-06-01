package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
}
