package dev.bookstore.creeper.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bookstore.creeper.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}
