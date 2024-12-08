package dev.bookstore.creeper.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t IN :tags")
    List<Book> findBookContainsTag(Set<String> tags);
}