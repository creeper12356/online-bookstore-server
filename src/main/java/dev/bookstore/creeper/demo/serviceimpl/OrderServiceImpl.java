package dev.bookstore.creeper.demo.serviceimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.OrderDAO;
import dev.bookstore.creeper.demo.dao.OrderItemDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.CreateCartItemRequestDTO;
import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;
import dev.bookstore.creeper.demo.dto.OrderUserDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.OrderService;
import lombok.Data;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    @Autowired
    private RestTemplate restTemplate;

    public OrderServiceImpl(
            OrderItemDAO orderItemDAO,
            OrderDAO orderDAO,
            UserDAO userDAO,
            BookDAO bookDAO) {
        this.orderItemDAO = orderItemDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @Override
    public GetItemsOkDTO<OrderDTO> getOrders(int userId, String query, Date from, Date to) {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));

        if (from != null && to != null && from.after(to)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<OrderDTO> orders = user
                .getOrders()
                .stream()
                .filter(order -> query.equals("")
                        || order.getOrderItems().stream().anyMatch(
                                item -> item.getBook().getTitle().toLowerCase().contains(query.toLowerCase())))
                .filter(order -> from == null || order.getTime().equals(from) || order.getTime().after(from))
                .filter(order -> to == null || order.getTime().equals(to) || order.getTime().before(to))
                .map(order -> new OrderDTO(order))
                .sorted(Comparator.comparing(OrderDTO::getId).reversed())
                .toList();
        return new GetItemsOkDTO<>(orders.size(), orders);
    }

    @Data
    private class PriceQuantityRequest {
        Integer price;
        Integer quantity;

        public PriceQuantityRequest(Integer price, Integer quantity) {
            this.price = price;
            this.quantity = quantity;
        }
    }

    @Override
    @Transactional
    public void createOrder(int userId, CreateOrderRequestDTO dto) {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));

        // 检查书籍库存
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> bookIds = dto
                .getBooks()
                .stream()
                .map(CreateCartItemRequestDTO::getBookId)
                .toList();
        List<Book> books = bookIds.stream().map(
                bookId -> bookDAO.findBookById(bookId)
                        .orElseThrow(() -> new NoSuchElementException("Book not found")))
                .toList();

        Order order = new Order(
                orderItems,
                user,
                dto.getReceiver(),
                dto.getTel(),
                dto.getAddress());

        int bookSize = books.size();
        int totalPrice = 0;
        for (int i = 0; i < bookSize; ++i) {
            if (books.get(i).getStock() < dto.getBooks().get(i).getNumber()) {
                // 库存不足
                throw new IllegalArgumentException("Stock not enough");
            }
            books.get(i).setStock(books.get(i).getStock() - dto.getBooks().get(i).getNumber());
            books.get(i).setSales(books.get(i).getSales() + dto.getBooks().get(i).getNumber());
            totalPrice += books.get(i).getPrice() * dto.getBooks().get(i).getNumber();
            // FIXME: 不使用微服务进行计算
            // // 向微服务CalPriceService发送请求
            // totalPrice += restTemplate.postForObject("http://CalPriceService/calprice", new PriceQuantityRequest(
            //         books.get(i).getPrice(), dto.getBooks().get(i).getNumber()), Integer.class);
            orderItems.add(new OrderItem(books.get(i), dto.getBooks().get(i).getNumber(), order));
        }

        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Balance not enough");
        }
        user.setBalance(user.getBalance() - totalPrice);

        bookDAO.saveAllBooks(books);

        orderItemDAO.saveOrderItems(orderItems);

        // int result = 10 / 0;
        orderDAO.saveOrder(order);
        // int result = 10 / 0;

        user.getOrders().add(order);
        userDAO.saveUser(user);
    }

    @Override
    public GetItemsOkDTO<OrderUserDTO> getAllOrders(int userId, String query, Date from, Date to) throws Exception {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if (!user.getIsAdmin()) {
            throw new IllegalArgumentException("Permission denied");
        }

        if (from != null && to != null && from.after(to)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<OrderUserDTO> orders = orderDAO
                .findAllOrders()
                .stream()
                .filter(order -> query.equals("")
                        || order.getOrderItems().stream().anyMatch(
                                item -> item.getBook().getTitle().toLowerCase().contains(query.toLowerCase())))
                .filter(order -> from == null || order.getTime().equals(from) || order.getTime().after(from))
                .filter(order -> to == null || order.getTime().equals(to) || order.getTime().before(to))
                .map(order -> new OrderUserDTO(order))
                .sorted(Comparator.comparing(OrderUserDTO::getId).reversed())
                .toList();

        return new GetItemsOkDTO<>(orders.size(), orders);
    }
}
