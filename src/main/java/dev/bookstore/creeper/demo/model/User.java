package dev.bookstore.creeper.demo.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Integer balance;

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;

    public User() {
        // not used
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.cartItems = new ArrayList<>();
    }
}
