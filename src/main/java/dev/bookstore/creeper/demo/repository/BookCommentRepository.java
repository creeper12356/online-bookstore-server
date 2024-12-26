package dev.bookstore.creeper.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import dev.bookstore.creeper.demo.model.Comment;

@Repository
public interface BookCommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(Integer bookId);

    @Query("{ 'id': ?0 }")
    @Update("{ '$set': {'replies': ?1 }}")
    void updateComments(String id, List<Comment> comments);
}
