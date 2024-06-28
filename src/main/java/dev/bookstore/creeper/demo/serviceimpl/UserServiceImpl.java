package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.OrderDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.BookSalesDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.UserPurchaseDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Order;
import dev.bookstore.creeper.demo.model.OrderItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;

    public UserServiceImpl(UserDAO userDAO, OrderDAO orderDAO) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public User getUserProfile(int userId) {
        return userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
    }

    @Override
    public void banUser(int currentUserId, int targetUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }

        User targetUser = userDAO.findUserById(targetUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (currentUserId == targetUserId) {
            throw new IllegalArgumentException("Cannot ban yourself.");
        }

        if (targetUser.getIsBanned()) {
            throw new IllegalArgumentException("User already banned.");
        }

        targetUser.setIsBanned(true);
        userDAO.saveUser(targetUser);
    }

    @Override
    public void unbanUser(int currentUserId, int targetUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }

        User targetUser = userDAO.findUserById(targetUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (currentUserId == targetUserId) {
            throw new IllegalArgumentException("Cannot unban yourself.");
        }

        if (!targetUser.getIsBanned()) {
            throw new IllegalArgumentException("User not banned.");
        }

        targetUser.setIsBanned(false);
        userDAO.saveUser(targetUser);
    }

    @Override
    public List<User> getAllUsers(int currentUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        if (!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }

        return userDAO.findAllUsers();
    }

    @Override
    public GetItemsOkDTO<UserPurchaseDTO> getUserRank(Integer currentUserId, Date from, Date to, Integer maxCount)
            throws Exception {
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

        // 统计每个用户的消费
        Map<User, Integer> userPurchase = new HashMap<>();
        for (Order order : orders) {
            if (userPurchase.containsKey(order.getUser())) {
                userPurchase.put(order.getUser(), userPurchase.get(order.getUser()) + order.getTotalPrice());
            } else {
                userPurchase.put(order.getUser(), order.getTotalPrice());
            }
        }

        // 将每个用户的消费排序
        List<Map.Entry<User, Integer>> sortedUserPurchase = userPurchase.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        List<UserPurchaseDTO> userPurchaseList = sortedUserPurchase
                .stream()
                .map(entry -> new UserPurchaseDTO(entry.getKey(), entry.getValue()))
                .limit(maxCount)
                .collect(Collectors.toList());
        return new GetItemsOkDTO<>(userPurchaseList.size(), userPurchaseList);
    }

    @Override
    public GetItemsOkDTO<BookSalesDTO> getUserStatistic(Integer currentUserId, Date from, Date to) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId).orElseThrow(() -> new NoSuchElementException());

        List<Order> userOrders = currentUser
                .getOrders()
                .stream()
                .filter(order -> from == null || order.getTime().after(from))
                .filter(order -> to == null || order.getTime().before(to))
                .collect(Collectors.toList());

        // 统计每本书购买的数量
        Map<Book, Integer> bookSaleMap = new HashMap<>();
        for(Order order: userOrders) {
            for(OrderItem orderItem: order.getOrderItems()) {
                if(bookSaleMap.containsKey(orderItem.getBook())) {
                    bookSaleMap.put(orderItem.getBook(), bookSaleMap.get(orderItem.getBook()) + orderItem.getNumber());
                } else {
                    bookSaleMap.put(orderItem.getBook(), orderItem.getNumber());
                }
            }
        }

        // 转成列表
        List<BookSalesDTO> bookSalesDTOs = bookSaleMap.entrySet()
                .stream()
                .map(entry -> new BookSalesDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new GetItemsOkDTO<>(bookSalesDTOs.size(), bookSalesDTOs);

    }

    @Override
    public void updateUserInfo(Integer userId, String username, String email, String avatar) throws Exception {
        User currentUser = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));

        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setAvatar(avatar);
        userDAO.saveUser(currentUser);
    }
}
