package dev.bookstore.creeper.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetItemsOkDTO<T> {
    private Integer total;
    private List<T> items;

    public GetItemsOkDTO(Integer total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
