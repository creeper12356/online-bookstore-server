package dev.bookstore.creeper.demo.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.repository.BookRepository;
import dev.bookstore.creeper.demo.service.BookService;
import dev.bookstore.creeper.demo.utils.PaginationUtils;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetAllBooksOkResponseDTO getAllBooks(
        String q, 
        Integer page, 
        Integer pagesize
        ) {
        List<Book> bookList = repository.findAll()
            .stream()
            .filter(book -> q.isEmpty() ? true :  book.getTitle()
            .contains(q)).collect(Collectors.toList());

        return new GetAllBooksOkResponseDTO(
            bookList.size(), 
            PaginationUtils.paginate(bookList, page, pagesize)
        );
    }

    @Override
    public Book getBookInfo(Integer id) {
        Book book = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        return book;
    }

    @Override
    public List<Comment> getBookComments(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"))
            .getComments();
    }

    @Override
    public void createBookComment(Integer id, String content) {
        Book book = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        book.getComments().add(new Comment("username", content));
        repository.save(book);
    }
    
}
