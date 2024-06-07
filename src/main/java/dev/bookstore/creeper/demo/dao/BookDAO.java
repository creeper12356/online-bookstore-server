package dev.bookstore.creeper.demo.dao;

import java.util.List;
import java.util.Optional;

import dev.bookstore.creeper.demo.model.Book;

public interface BookDAO {
    List<Book> findAllBooks();
    Optional<Book> findBookById(Integer id);
    List<Book> findAllBooksById(List<Integer> ids);
    void saveBook(Book book);
    void saveAllBooks(List<Book> books);
    void deleteBook(Book book);
}
