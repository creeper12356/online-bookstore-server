package dev.bookstore.creeper.demo.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

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
    public User getUserProfile(int userId) {
        return userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
    }

    @Override
    public void banUser(int currentUserId, int targetUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if(!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }

        User targetUser = userDAO.findUserById(targetUserId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if(currentUserId == targetUserId) {
            throw new IllegalArgumentException("Cannot ban yourself.");
        }

        if(targetUser.getIsBanned()) {
            throw new IllegalArgumentException("User already banned.");
        }

        targetUser.setIsBanned(true);
        userDAO.saveUser(targetUser);
    }

    @Override
    public void unbanUser(int currentUserId, int targetUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if(!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }

        User targetUser = userDAO.findUserById(targetUserId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if(currentUserId == targetUserId) {
            throw new IllegalArgumentException("Cannot unban yourself.");
        }

        if(!targetUser.getIsBanned()) {
            throw new IllegalArgumentException("User not banned.");
        }

        targetUser.setIsBanned(false);
        userDAO.saveUser(targetUser);
    }

    @Override
    public List<User> getAllUsers(int currentUserId) throws Exception {
        User currentUser = userDAO.findUserById(currentUserId).orElseThrow(() -> new NoSuchElementException("User not found."));
        if(!currentUser.getIsAdmin()) {
            throw new AuthenticationException("Permission denied.");
        }
        
        return userDAO.findAllUsers();
    }
}
