package dev.bookstore.creeper.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}