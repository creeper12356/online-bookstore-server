package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.bookstore.creeper.demo.dao.OrderItemDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.repository.OrderItemRepository;

@Component
public class OrderItemDAOImpl implements OrderItemDAO {

    private final OrderItemRepository orderItemRepository;
    public OrderItemDAOImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public List<OrderItem> findAllOrderItemsByBook(Book book) {
        return orderItemRepository.findAllByBook(book);
    }
    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void saveOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
        // int result = 10 / 0;
	}
    
}
