package dev.bookstore.creeper.demo.service;

import java.util.List;

import dev.bookstore.creeper.demo.model.Book;

public interface BookService {
    List<Book> getAllBooks(String q, Integer page, Integer pagesize);
    Book getBookInfo(Integer id);
}
