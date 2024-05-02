package dev.bookstore.creeper.demo.controller;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GetBookCommentsOkResponseDTO;
import dev.bookstore.creeper.demo.dto.PostBookCommentRequestDTO;
import dev.bookstore.creeper.demo.model.Comment;
import dev.bookstore.creeper.demo.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
                return ResponseEntity.ok(new GetAllBooksOkResponseDTO(service.getAllBooks(q, page, pagesize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookInfo(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.ok(service.getBookInfo(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> getBookComments(
            @PathVariable Integer id
    ) {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1,"user1","This is a comment.", new Date()));
        comments.add(new Comment(2, "user2", "This is a reply",  new Date()));
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
