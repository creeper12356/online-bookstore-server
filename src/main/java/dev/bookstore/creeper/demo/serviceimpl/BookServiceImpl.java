package dev.bookstore.creeper.demo.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.service.BookService;
import dev.bookstore.creeper.demo.utils.PaginationUtils;


@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public GetAllBooksOkResponseDTO getAllBooks(
        String q, 
        Integer page, 
        Integer pagesize
        ) {
        List<Book> bookList = bookDAO.findAllBooks()
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
        Book book = bookDAO.findBookById(id).orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        return book;
    }

    @Override
    public List<Comment> getBookComments(Integer id) {
        return bookDAO.findBookById(id)
            .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"))
            .getComments();
    }

    @Override
    public void createBookComment(Integer id, String content) {
        Book book = bookDAO.findBookById(id).orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        book.getComments().add(new Comment("username", content));
        bookDAO.saveBook(book);
    }
    
}
