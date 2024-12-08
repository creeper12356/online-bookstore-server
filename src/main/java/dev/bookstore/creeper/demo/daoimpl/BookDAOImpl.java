package dev.bookstore.creeper.demo.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.repository.BookCommentRepository;
import dev.bookstore.creeper.demo.repository.BookRepository;
import jakarta.annotation.PostConstruct;

@Component
public class BookDAOImpl implements BookDAO {
    private final BookRepository bookRepository;
    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private final Gson gson = new Gson();

    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void cacheAllBooks() {
        System.out.println("cache all books to redis when boot");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("cacheAllBooksTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // TODO: figure out why this works
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            List<Book> books = bookRepository.findAll();
            for(Book book : books) {
                redisTemplate.opsForValue().set("book" + book.getId(), gson.toJson(book));
            }
            transactionManager.commit(status);
        } catch(Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    public List<Book> findAllBooks(String q) {
        List<Book> books = new ArrayList<>();
        Set<String> redisKeys = redisTemplate.keys("book*");
        for (String redisKey : redisKeys) {
            Book book = gson.fromJson(redisTemplate.opsForValue().get(redisKey), Book.class);
            if (q.isEmpty() || book.getTitle().toLowerCase().contains(q.toLowerCase())) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public Optional<Book> findBookById(Integer id) {
        String redisFindRes = (String) redisTemplate.opsForValue().get("book" + id);
        if (redisFindRes != null) {
            System.out.println("Find book with id " + id + " in redis cache.");
            Book book = gson.fromJson(redisFindRes, Book.class);
            book.setComments(bookCommentRepository.findByBookId(id));
            return Optional.of(book);
        } else {
            // TODO: code not reached
            System.out.println("Cannot find book with id " + id + " in redis cache, update cache.");
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isPresent()) {
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
        bookCommentRepository.saveAll(book.getComments());
    }

    @Override
    public List<Book> findAllBooksById(List<Integer> ids) {
        // find in redis cache
        List<Book> books = new ArrayList<>();
        for(Integer id: ids) {
            String redisFindRes = (String) redisTemplate.opsForValue().get("book" + id);
            if(redisFindRes == null) {
                continue;
            }
            books.add(gson.fromJson(redisFindRes, Book.class));
        }

        return books;
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        System.out.println("Force update redis cache for a list of book are updated.");
        for (Book book : books) {
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
