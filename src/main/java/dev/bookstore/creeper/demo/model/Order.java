package dev.bookstore.creeper.demo.model;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "order")
    @Cascade(value = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "tel")
    private String tel;

    @Column(name = "address")
    private String address;

    @Column(name = "time")
    private Date time;

    public Order() {    
        // not used
    }

    public Order(List<OrderItem> orderItems, User user, String receiver, String tel, String address) {
        this.orderItems = orderItems;
        this.user = user;
        this.receiver = receiver;
        this.tel = tel;
        this.address = address;
        this.time = Date.from(Instant.now());
    }
}
