package dev.bookstore.creeper.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.model.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    @Query("SELECT ua FROM UserAuth ua WHERE ua.user = ?1 AND ua.password = ?2")
    Optional<UserAuth> findByUserAndPassword(User user, String password);
}
