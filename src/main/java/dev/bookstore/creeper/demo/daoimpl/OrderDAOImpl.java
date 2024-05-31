package dev.bookstore.creeper.demo.daoimpl;

import org.springframework.stereotype.Component;

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
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
    
}
