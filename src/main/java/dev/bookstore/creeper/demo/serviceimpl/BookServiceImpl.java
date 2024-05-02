package dev.bookstore.creeper.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.repository.BookRepository;
import dev.bookstore.creeper.demo.service.BookService;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks(String q, Integer page, Integer pagesize) {
        List<Book> bookList = repository.findAll()
            .stream()
            .filter(book -> q.isEmpty() ? true :  book.getTitle()
            .contains(q)).collect(Collectors.toList());

        int fromIndex = page * pagesize;
        int toIndex = Math.min(page * pagesize + pagesize, bookList.size());

        return fromIndex > toIndex ? new ArrayList<>() : bookList.subList(fromIndex, toIndex);
    }

    @Override
    public Book getBookInfo(Integer id) {
        Book book = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        return book;
    }
    
}
