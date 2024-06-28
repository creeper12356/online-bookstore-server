package dev.bookstore.creeper.demo.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL)
    private UserAuth userAuth;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User() {
        // not used
    }

    public User(String username, String password, String email, Boolean isAdmin) {
        this.username = username;
        this.userAuth = new UserAuth(this, password);
        this.email = email;
        this.balance = 5000; // 初始余额50元
        this.avatar = "";
        this.isAdmin = isAdmin;
        this.isBanned = false;
        this.cartItems = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
