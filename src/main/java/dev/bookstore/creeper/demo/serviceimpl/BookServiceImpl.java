package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.BookTagDAO;
import dev.bookstore.creeper.demo.dao.CartItemDAO;
import dev.bookstore.creeper.demo.dao.OrderDAO;
import dev.bookstore.creeper.demo.dao.OrderItemDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.BookSalesDTO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.UpdateBookInfoDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.BookService;
import dev.bookstore.creeper.demo.utils.PaginationUtils;

@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final CartItemDAO cartItemDAO;

    @Autowired
    private BookTagDAO bookTagDAO;

    public BookServiceImpl(
            BookDAO bookDAO,
            UserDAO userDAO,
            OrderDAO orderDAO,
            OrderItemDAO orderItemDAO,
            CartItemDAO cartItemDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public GetAllBooksOkResponseDTO getAllBooks(
            String q,
            Integer page,
            Integer pagesize) {
        List<Book> bookList = bookDAO.findAllBooks(q);
        return new GetAllBooksOkResponseDTO(
                bookList.size(),
                PaginationUtils.paginate(bookList, page, pagesize));
    }

    @Override
    public Book getBookInfo(Integer id) {
        Book book = bookDAO.findBookById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
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
        Book book = bookDAO.findBookById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        book.getComments().add(new Comment(id, "username", content));
        bookDAO.saveBook(book);
    }

    @Override
    public Integer createBook(Integer userId, UpdateBookInfoDTO book) throws Exception {
        User user = userDAO.findUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        if (!user.getIsAdmin()) {
            throw new AuthenticationException("User is not an admin");
        }
        Book newBook = new Book(book);
        bookDAO.saveBook(newBook);

        return newBook.getId();
    }

    @Override
    public void updateBookInfo(
            Integer userId,
            Integer bookId,
            UpdateBookInfoDTO dto) throws Exception {
        User user = userDAO.findUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        if (!user.getIsAdmin()) {
            throw new AuthenticationException("User is not an admin");
        }
        Book book = bookDAO.findBookById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));
        book.updateInfo(dto);
        bookDAO.saveBook(book);
    }

    @Override
    public void deleteBook(Integer userId, Integer bookId) throws Exception {
        User user = userDAO.findUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        if (!user.getIsAdmin()) {
            throw new AuthenticationException("User is not an admin");
        }
        Book book = bookDAO.findBookById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));

        List<CartItem> affectedCartItems = cartItemDAO.findAllCartItemsByBook(book);
        for (CartItem cartItem : affectedCartItems) {
            cartItemDAO.deleteCartItem(cartItem);
        }

        List<OrderItem> affecOrderItems = orderItemDAO.findAllOrderItemsByBook(book);
        for (OrderItem orderItem : affecOrderItems) {
            orderItemDAO.deleteOrderItem(orderItem);
        }

        bookDAO.deleteBook(book);
    }

    @Override
    public GetItemsOkDTO<BookSalesDTO> getBookRank(Integer currentUserId, Date from, Date to, Integer maxCount) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }
        
        List<Order> orders = orderDAO.findAllOrders()
                .stream()
                .filter(order -> from == null || order.getTime().after(from))
                .filter(order -> to == null || order.getTime().before(to))
                .collect(Collectors.toList());

        // 统计每本书的销量
        Map<Book, Integer> bookSales = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                if (bookSales.containsKey(orderItem.getBook())) {
                    bookSales.put(orderItem.getBook(), bookSales.get(orderItem.getBook()) + orderItem.getNumber());
                } else {
                    bookSales.put(orderItem.getBook(), orderItem.getNumber());
                }
            }
        }

        // 将每本书按销量排序
        List<Map.Entry<Book, Integer>> sortedBookSales = bookSales.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        List<BookSalesDTO> bookSalesList = sortedBookSales
                .stream()
                .map(entry -> new BookSalesDTO(entry.getKey(), entry.getValue()))
                .limit(maxCount)
                .collect(Collectors.toList());
        return new GetItemsOkDTO<>(bookSalesList.size(), bookSalesList);
    }

    @Override
    public List<Book> getSimilarBooks(Integer id) {
        Book book = bookDAO.findBookById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        return bookDAO.findSimilarBooksByTags(book);
    }

    @Override
    public void updateBookTags(Integer id, List<String> tags) {
        Book book = bookDAO.findBookById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        for(String tag: tags) {
            // 保证tag存在
            bookTagDAO.createTag(tag);
        }
        book.setTags(tags);
        bookDAO.saveBook(book);
    }

}
