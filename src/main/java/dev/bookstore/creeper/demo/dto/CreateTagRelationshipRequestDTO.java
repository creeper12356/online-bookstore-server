package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class CreateTagRelationshipRequestDTO {
    private String tag1;
    private String tag2;
}
