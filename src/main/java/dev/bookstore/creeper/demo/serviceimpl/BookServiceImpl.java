package dev.bookstore.creeper.demo.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;


import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.CartItemDAO;
import dev.bookstore.creeper.demo.dao.OrderItemDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.UpdateBookInfoDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.BookService;
import dev.bookstore.creeper.demo.utils.PaginationUtils;


@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderItemDAO orderItemDAO;
    private final CartItemDAO cartItemDAO;


    public BookServiceImpl(BookDAO bookDAO, UserDAO userDAO, OrderItemDAO orderItemDAO, CartItemDAO cartItemDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.orderItemDAO = orderItemDAO;
        this.cartItemDAO = cartItemDAO;
    }


    @Override
    public GetAllBooksOkResponseDTO getAllBooks(
        String q, 
        Integer page, 
        Integer pagesize
        ) {
        List<Book> bookList = bookDAO.findAllBooks()
            .stream()
            .filter(book -> q.isEmpty() ? true : book.getTitle().toLowerCase().contains(q.toLowerCase()))
            .collect(Collectors.toList());

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

    @Override
    public void updateBookInfo(
        Integer userId, 
        Integer bookId, 
        UpdateBookInfoDTO dto
    ) throws Exception {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        if(!user.getIsAdmin()) {
            throw new AuthenticationException("User is not an admin");
        }
        Book book = bookDAO.findBookById(bookId).orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));
        book.updateInfo(dto);
        bookDAO.saveBook(book);
    }

    @Override
    public void deleteBook(Integer userId, Integer bookId) throws Exception{
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        if(!user.getIsAdmin()) {
            throw new AuthenticationException("User is not an admin");
        }
        Book book = bookDAO.findBookById(bookId).orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));
        
        List<CartItem> affectedCartItems = cartItemDAO.findAllCartItemsByBook(book);
        for(CartItem cartItem : affectedCartItems) {
            cartItemDAO.deleteCartItem(cartItem);
        }

        List<OrderItem> affecOrderItems = orderItemDAO.findAllOrderItemsByBook(book);
        for(OrderItem orderItem : affecOrderItems) {
            orderItemDAO.deleteOrderItem(orderItem);
        }

        bookDAO.deleteBook(book);
    }
    
}
