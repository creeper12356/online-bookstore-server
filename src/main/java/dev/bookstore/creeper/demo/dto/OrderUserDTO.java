package dev.bookstore.creeper.demo.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.bookstore.creeper.demo.model.Order;
import lombok.Data;

/**
 * 带有用户信息的userDTO
 */
@Data
public class OrderUserDTO {
    private Integer id;
    private List<OrderBookDTO> books;
    private UserBriefDTO user;
    private String receiver;
    private String tel;
    private String address;
    private Integer totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date time;

    public OrderUserDTO(Order order) {
        this.id = order.getId();
        this.books = order
            .getOrderItems()
            .stream()
            .filter(orderItem -> orderItem.getBook() != null)
            .map(
                orderItem -> new OrderBookDTO(
                    orderItem.getBook(), 
                    orderItem.getNumber() 
                )
            )
            .toList();
        this.user = new UserBriefDTO(order.getUser());
            
        this.receiver = order.getReceiver();
        this.tel = order.getTel();
        this.address = order.getAddress();
        this.time = order.getTime();
        this.totalPrice = order.getOrderItems().stream()
            .mapToInt(
                orderItem -> orderItem.getBook().getPrice() * orderItem.getNumber()
            )
            .sum();
    }
}
