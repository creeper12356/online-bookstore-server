package dev.bookstore.creeper.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Comment;

@Repository
public interface BookCommentRepository extends MongoRepository<Comment, Integer> {
    List<Comment> findByBookId(Integer bookId);
}
