package dev.bookstore.creeper.demo.serviceimpl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.UserRepository;
import dev.bookstore.creeper.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getMe(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));
    }
}
