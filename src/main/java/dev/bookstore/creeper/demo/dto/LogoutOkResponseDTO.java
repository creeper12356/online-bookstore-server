package dev.bookstore.creeper.demo.dto;

import lombok.Data;

@Data
public class LogoutOkResponseDTO {
    private Boolean ok;
    private Long duration; 

    public LogoutOkResponseDTO(Long duration) {
        this.ok = true;
        this.duration = duration;
    }
}
