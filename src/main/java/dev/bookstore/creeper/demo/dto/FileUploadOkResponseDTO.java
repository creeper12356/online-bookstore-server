package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class FileUploadOkResponseDTO {
    private String status;
    private String url;
}
