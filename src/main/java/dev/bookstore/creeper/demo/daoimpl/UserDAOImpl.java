package dev.bookstore.creeper.demo.daoimpl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.UserRepository;

@Component
public class UserDAOImpl implements UserDAO {
    private final UserRepository userRepository;
    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    
}
