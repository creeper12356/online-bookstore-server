package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.BookCommentDAO;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.repository.BookCommentRepository;

@Component
public class BookCommentDAOImpl implements BookCommentDAO {
    @Autowired
    private BookCommentRepository repository;


    public Optional<Comment> findCommentById(String id) {
        return repository.findById(id);
    }

    public void updateComments(String id, List<Comment> comments) {
        repository.updateComments(id, comments);
    }
}
