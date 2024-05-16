package dev.bookstore.creeper.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bookstore.creeper.demo.model.Auth;
import dev.bookstore.creeper.demo.model.User;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    public Optional<Auth> findByToken(String token);
    public Optional<Auth> findByUser(User user);
}
