package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.CartItem;
import lombok.Data;

@Data
public class CartItemDTO {
    private Integer id;
    private Integer bookId;
    private String title;
    private Integer number;
    private Integer price;
    
    
    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.bookId = cartItem.getBook().getId();
        this.title = cartItem.getBook().getTitle();
        this.number = cartItem.getNumber();
        this.price = cartItem.getBook().getPrice();
    }
}
