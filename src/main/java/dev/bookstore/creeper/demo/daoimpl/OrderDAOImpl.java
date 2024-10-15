package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.bookstore.creeper.demo.dao.OrderDAO;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.repository.OrderRepository;

@Component
public class OrderDAOImpl implements OrderDAO {
    private final OrderRepository orderRepository;

    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrder(Order order) {
        orderRepository.save(order);
        // int result = 10 / 0;
    }


    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
