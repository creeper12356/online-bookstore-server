package dev.bookstore.creeper.demo.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.bookstore.creeper.demo.model.Order;
import lombok.Data;

@Data
public class OrderDTO {
    private Integer id;
    private List<OrderBookDTO> books;
    private String receiver;
    private String tel;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.books = order
            .getOrderItems()
            .stream()
            .map(
                orderItem -> new OrderBookDTO(
                    orderItem.getBook(), 
                    orderItem.getNumber()
                )
            )
            .toList();
            
        this.receiver = order.getReceiver();
        this.tel = order.getTel();
        this.address = order.getAddress();
        this.time = order.getTime();
    }
}
