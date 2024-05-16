package dev.bookstore.creeper.demo.service;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.User;

public interface AuthService {
    User getUserByToken(String token) throws NoSuchElementException;

    void register(RegisterRequestDTO requestDTO) throws IllegalArgumentException;
    String login(RegisterRequestDTO requestDTO) throws AuthenticationException;
}
