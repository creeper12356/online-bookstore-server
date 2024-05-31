package dev.bookstore.creeper.demo.daoimpl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.UserAuthDAO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.model.UserAuth;
import dev.bookstore.creeper.demo.repository.UserAuthRepository;

@Component
public class UserAuthDAOImpl implements UserAuthDAO {

    private final UserAuthRepository userAuthRepository;

    public UserAuthDAOImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }
    @Override
    public Optional<UserAuth> findUserAuthById(Integer id) {
        return userAuthRepository.findById(id);
    }
    @Override
    public Optional<UserAuth> findUserAuthByUserAndPassword(User user, String password) {
        return userAuthRepository.findByUserAndPassword(user, password);
    }
    
}
