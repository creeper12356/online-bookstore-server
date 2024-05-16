package dev.bookstore.creeper.demo.service;

import java.util.List;

import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Comment;

public interface BookService {
    GetAllBooksOkResponseDTO getAllBooks(String q, Integer page, Integer pagesize);
    Book getBookInfo(Integer id);
    List<Comment> getBookComments(Integer id);
    void createBookComment(Integer id, String content);
}
