package dev.bookstore.creeper.demo.service;

import dev.bookstore.creeper.demo.model.User;

public interface UserService {
    User getMe(String token) throws Exception;
}
