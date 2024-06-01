package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Integer balance;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.balance = user.getBalance();
        this.email = user.getEmail();
    }
}
