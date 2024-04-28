package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class PostBookCommentRequestDTO {
    private String content;
    public PostBookCommentRequestDTO(String content) {
        this.content = content;
    }
}
