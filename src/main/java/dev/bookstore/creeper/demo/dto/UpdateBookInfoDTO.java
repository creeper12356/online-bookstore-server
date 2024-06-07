package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class UpdateBookInfoDTO {
    private String title;
    private String author;
    private String description;
    private String isbn;
    private Integer price;
    private String cover;
    private Integer sales;
    private Integer stock;
}