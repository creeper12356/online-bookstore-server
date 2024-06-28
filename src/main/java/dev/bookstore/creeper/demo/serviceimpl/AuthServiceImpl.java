package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.UserAuthDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final UserAuthDAO userAuthDAO;

    public AuthServiceImpl(
            UserDAO userDAO,
            UserAuthDAO userAuthDAO) {
        this.userDAO = userDAO;
        this.userAuthDAO = userAuthDAO;
    }

    public void register(RegisterRequestDTO requestDTO) throws IllegalArgumentException {
        Optional<User> optionalUser = userDAO.findUserByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User newUser = new User(requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail(), false);
        userDAO.saveUser(newUser);
    }

    public User login(RegisterRequestDTO requestDTO) throws AuthenticationException {
        Optional<User> optionalUser = userDAO.findUserByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userAuthDAO.findUserAuthByUserAndPassword(user, requestDTO.getPassword()).isPresent()) {
                // 在数据库内校验密码
                
                if(user.getIsBanned()) {
                    throw new AuthenticationException("Banned user cannot login.");
                }
                return user;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
}
