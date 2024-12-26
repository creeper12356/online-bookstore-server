package dev.bookstore.creeper.demo.dao;

import java.util.List;
import java.util.Optional;

import dev.bookstore.creeper.demo.model.Comment;

public interface BookCommentDAO {
    Optional<Comment> findCommentById(String id);
    void updateComments(String id, List<Comment> comments);
}
