package dev.bookstore.creeper.demo.serviceimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dto.CreateCartItemRequestDTO;
import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.BookRepository;
import dev.bookstore.creeper.demo.repository.OrderRepository;
import dev.bookstore.creeper.demo.service.AuthService;
import dev.bookstore.creeper.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderServiceImpl(
        AuthService authService, 
        OrderRepository orderRepository,
        BookRepository bookRepository) 
    {
        this.authService = authService;
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    @Override 
    public GetItemsOkDTO<OrderDTO> getOrders(String token) throws AuthenticationException {
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("Invalid token");
        }
        List<OrderDTO> orders = user
                                .getOrders()
                                .stream()
                                .map(order -> new OrderDTO(order))
                                .sorted(Comparator.comparing(OrderDTO::getId).reversed())
                                .toList();
        return new GetItemsOkDTO<>(orders.size(), orders);
    }

    @Override
    public void createOrder(String token, CreateOrderRequestDTO dto) throws AuthenticationException {
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("Invalid token");
        }

        // 检查书籍库存
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> bookIds = dto
                                .getBooks()
                                .stream()
                                .map(CreateCartItemRequestDTO::getBookId)
                                .toList();
        List<Book> books = bookRepository.findAllById(bookIds);

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

        orderRepository.save(order);

        user.getOrders().add(order);
    }
}
