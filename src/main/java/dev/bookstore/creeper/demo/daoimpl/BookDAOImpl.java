package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.repository.BookRepository;

@Component
public class BookDAOImpl implements BookDAO {
    private final BookRepository bookRepository;
    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
    @Override
    public List<Book> findAllBooksById(List<Integer> ids) {
        return bookRepository.findAllById(ids);
    }
    @Override
    public void saveAllBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }
    
}
