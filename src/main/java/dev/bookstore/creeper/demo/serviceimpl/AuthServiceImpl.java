package dev.bookstore.creeper.demo.serviceimpl;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.Auth;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.AuthRepository;
import dev.bookstore.creeper.demo.repository.UserRepository;
import dev.bookstore.creeper.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    public User getUserByToken (String token) throws NoSuchElementException {
        User user = authRepository
                        .findByToken(token)
                        .orElseThrow(() -> new NoSuchElementException("User not found."))
                        .getUser();
        return user;
    }

    public void register(RegisterRequestDTO requestDTO) throws IllegalArgumentException{
        Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getUsername());
        if(optionalUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User newUser = new User(requestDTO.getUsername(), requestDTO.getPassword());
        userRepository.save(newUser);
    }

    public String login(RegisterRequestDTO requestDTO) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(requestDTO.getPassword())) {
                return generateAuthToken(user);
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }

    private String generateAuthToken(User user) {
        Optional<Auth> optionalAuth = authRepository.findByUser(user);
        Auth auth;
        if (optionalAuth.isPresent()) {
            // 用户已经拥有令牌
            auth = optionalAuth.get();
            auth.updateToken();
        } else {
            // 用户没有令牌
            auth = new Auth(user);
        }

        authRepository.save(auth);
        return auth.getToken();
    }
}
