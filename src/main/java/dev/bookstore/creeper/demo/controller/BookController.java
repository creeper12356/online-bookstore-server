package dev.bookstore.creeper.demo.controller;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GetBookCommentsOkResponseDTO;
import dev.bookstore.creeper.demo.dto.PostBookCommentRequestDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/books")
public class BookController {
    @GetMapping
    public ResponseEntity<Object> getAllBooks(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "The Great Gatsby is a novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.", 10, "https://images-na.ssl-images-amazon.com/images/I/51ZQj5jJN-L._SX331_BO1,204,203,200_.jpg", 100));
        bookList.add(new Book(2, "To Kill a Mockingbird", "Harper Lee", "To Kill a Mockingbird is a novel by Harper Lee published in 1960. Instantly successful, widely read in high schools and middle schools in the United States, it has become a classic of modern American literature, winning the Pulitzer Prize.", 15, "https://images-na.ssl-images-amazon.com/images/I/51ZQj5jJN-L._SX331_BO1,204,203,200_.jpg", 200));
        bookList.add(new Book(3, "1984", "George Orwell", "1984 is a dystopian social science fiction novel by English novelist George Orwell. It was published on 8 June 1949 by Secker & Warburg as Orwell's ninth and final book completed in his lifetime.", 20, "https://images-na.ssl-images-amazon.com/images/I/51ZQj5jJN-L._SX331_BO1,204,203,200_.jpg", 300));

        return ResponseEntity.ok(new GetAllBooksOkResponseDTO(bookList));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(new Book(id, "The Great Gatsby", "F. Scott Fitzgerald", "The Great Gatsby is a novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.", 10, "https://images-na.ssl-images-amazon.com/images/I/51ZQj5jJN-L._SX331_BO1,204,203,200_.jpg", 100));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> getBookComments(
            @PathVariable Integer id
    ) {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "user1", "This is a comment", null, 10, false, new Date()));
        comments.add(new Comment(2, "user2", "This is a reply", "user1", 5, true, new Date()));
        return ResponseEntity.ok(new GetBookCommentsOkResponseDTO(comments));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Object> postBookComment(
            @PathVariable Integer id,
            @RequestBody PostBookCommentRequestDTO dto
            ) {
        return ResponseEntity.ok(new GeneralResponseDTO(true, dto.getContent()));
    }
}
