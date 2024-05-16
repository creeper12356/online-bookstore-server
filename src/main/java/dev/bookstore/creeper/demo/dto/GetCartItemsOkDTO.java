package dev.bookstore.creeper.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetCartItemsOkDTO {
    Integer total;
    List<CartItemDTO> items;

    public GetCartItemsOkDTO(Integer total, List<CartItemDTO> items) {
        this.total = total;
        this.items = items;
    }
}
