package dev.bookstore.creeper.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;

    private Integer bookId;

    private String username;

    private String content;

    private Date time;

    private List<Comment> replies;

    private String replyTo;

    public Comment() {
        // not used
    }
    public Comment(Integer bookId, String username, String content, String reply_to) {
        this.bookId = bookId;
        this.username = username;
        this.content = content;
        this.time = new Date();
        this.replies = new ArrayList<>();
        this.replyTo = reply_to;
    }
}
