package dev.bookstore.creeper.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateOrderRequestDTO {
    Integer userId;
    List<CreateCartItemRequestDTO> books;
    String receiver;
    String address;
    String tel;
}
