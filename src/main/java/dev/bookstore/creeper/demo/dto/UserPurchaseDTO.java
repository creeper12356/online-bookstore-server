package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.User;
import lombok.Data;

@Data
public class UserPurchaseDTO {
    private Integer id;
    private String username;
    private String avatar;
    private String email;
    private Integer purchase;

    public UserPurchaseDTO(User user, Integer purchase) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.purchase = purchase;
        this.email = user.getEmail();
    }
}
