package dev.bookstore.creeper.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private Date time;

    public Comment() {
        // not used
    }
    public Comment(Integer id, String username, String content, Date time) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.time = time;
    }
}
