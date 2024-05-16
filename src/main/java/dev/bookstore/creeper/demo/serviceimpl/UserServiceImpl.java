package dev.bookstore.creeper.demo.serviceimpl;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.AuthService;
import dev.bookstore.creeper.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    public final AuthService authService;

    public UserServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public User getMe(String token) throws Exception {
        try {
            return authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("Authentication fails.");
        }
    }
}
