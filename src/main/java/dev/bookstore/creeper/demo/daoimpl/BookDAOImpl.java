package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.repository.BookRepository;

@Component
public class BookDAOImpl implements BookDAO {
    private final BookRepository bookRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final Gson gson = new Gson();
    

    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println("Force update redis cache for a list of books are searched.");
        for(Book book : books) {
            redisTemplate.opsForValue().set("book" + book.getId(), gson.toJson(book));
        }
        return books;
    }

    @Override
    public Optional<Book> findBookById(Integer id) {
        String redisFindRes = (String) redisTemplate.opsForValue().get("book" + id);
        if(redisFindRes != null) {
            System.out.println("Find book with id " + id + " in redis cache.");
            return Optional.of(gson.fromJson(redisFindRes, Book.class));
        } else {
            System.out.println("Cannot find book with id " + id + " in redis cache, update cache.");
            Optional<Book> optionalBook = bookRepository.findById(id);
            if(optionalBook.isPresent()) {
                redisTemplate.opsForValue().set("book" + id, gson.toJson(optionalBook.get()));
            }
            return optionalBook;
        }
    }

    @Override
    public void saveBook(Book book) {
        System.out.println("Force update redis cache for book with id " + book.getId() + " is updated.");
        redisTemplate.opsForValue().set("book" + book.getId(), gson.toJson(book));
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooksById(List<Integer> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        System.out.println("Force update redis cache for a list of book are updated.");
        for(Book book : books) {
            redisTemplate.opsForValue().set("book" + book.getId(), gson.toJson(book));
        }

        bookRepository.saveAll(books);
    }

    @Override
    public void deleteBook(Book book) {
        System.out.println("Force update redis cache for book with id " + book.getId() + " is deleted.");
        redisTemplate.opsForValue().getAndDelete("book" + book.getId());

        bookRepository.delete(book);
    }

}
