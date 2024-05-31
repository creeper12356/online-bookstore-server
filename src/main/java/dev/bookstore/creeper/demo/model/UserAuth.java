package dev.bookstore.creeper.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "userAuth")
    private User user;

    public UserAuth() {
        // not used
    }
    public UserAuth(User user, String password) {
        this.user = user;
        this.password = password;
    }
}
