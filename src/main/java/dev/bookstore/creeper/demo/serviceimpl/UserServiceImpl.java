package dev.bookstore.creeper.demo.serviceimpl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    public final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getMe(int userId) {
        return userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
    }
}
