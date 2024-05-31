package dev.bookstore.creeper.demo.serviceimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.OrderDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.CreateCartItemRequestDTO;
import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    public OrderServiceImpl(
        OrderDAO orderDAO,
        UserDAO userDAO,
        BookDAO bookDAO
    ) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @Override 
    public GetItemsOkDTO<OrderDTO> getOrders(int userId) {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
        List<OrderDTO> orders = user
                                .getOrders()
                                .stream()
                                .map(order -> new OrderDTO(order))
                                .sorted(Comparator.comparing(OrderDTO::getId).reversed())
                                .toList();
        return new GetItemsOkDTO<>(orders.size(), orders);
    }

    @Override
    public void createOrder(int userId, CreateOrderRequestDTO dto) {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));

        // 检查书籍库存
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> bookIds = dto
                                .getBooks()
                                .stream()
                                .map(CreateCartItemRequestDTO::getBookId)
                                .toList();
        List<Book> books = bookDAO.findAllBooksById(bookIds);

        if(books.size() != bookIds.size()) {
            // 有书籍不存在
            throw new NoSuchElementException("Book not found");
        }

        Order order = new Order(
                    orderItems, 
                    user, 
                    dto.getReceiver(), 
                    dto.getTel(), 
                    dto.getAddress()
                );
                
        int bookSize = books.size();
        for(int i = 0;i < bookSize; ++i) {
            orderItems.add(new OrderItem(books.get(i), dto.getBooks().get(i).getNumber(),order));
        }

        orderDAO.saveOrder(order);

        user.getOrders().add(order);
    }
}
