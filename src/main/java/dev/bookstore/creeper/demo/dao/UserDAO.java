package dev.bookstore.creeper.demo.dao;

import java.util.List;
import java.util.Optional;

import dev.bookstore.creeper.demo.model.User;

public interface UserDAO {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(Integer id);
    void saveUser(User user);
    List<User> findAllUsers();
}
