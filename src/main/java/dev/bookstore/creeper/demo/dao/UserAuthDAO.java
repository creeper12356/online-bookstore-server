package dev.bookstore.creeper.demo.dao;

import java.util.Optional;

import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.model.UserAuth;

public interface UserAuthDAO {
    Optional<UserAuth> findUserAuthById(Integer id);
    Optional<UserAuth> findUserAuthByUserAndPassword(User user, String password);
}
