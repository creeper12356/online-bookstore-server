package dev.bookstore.creeper.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    private Integer id;
    private String username;
    private String content;
    private String reply;
    private Integer like;
    private Boolean liked;
    private Date createdAt;

    public Comment() {
        // not used
    }
    public Comment(Integer id, String username, String content, String reply, Integer like, Boolean liked, Date createdAt) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.reply = reply;
        this.like = like;
        this.liked = liked;
        this.createdAt = createdAt;
    }
}
