package dev.bookstore.creeper.demo.dto;

import dev.bookstore.creeper.demo.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class GetBookCommentsOkResponseDTO {
    private Integer total;
    private List<Comment> items;

    public GetBookCommentsOkResponseDTO(List<Comment> comments) {
        this.total = comments.size();
        this.items = comments;
    }
}
