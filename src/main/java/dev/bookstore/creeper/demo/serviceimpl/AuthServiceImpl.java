package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.UserAuthRepository;
import dev.bookstore.creeper.demo.repository.UserRepository;
import dev.bookstore.creeper.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    public AuthServiceImpl(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
    }

    public void register(RegisterRequestDTO requestDTO) throws IllegalArgumentException {
        Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User newUser = new User(requestDTO.getUsername(), requestDTO.getPassword());
        userRepository.save(newUser);
    }

    public User login(RegisterRequestDTO requestDTO) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(userAuthRepository.findByUserAndPassword(user, requestDTO.getPassword()).isPresent()) {
                // 在数据库内校验密码
                return user;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
}
