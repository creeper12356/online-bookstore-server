package dev.bookstore.creeper.demo.controller;

import dev.bookstore.creeper.demo.dto.BookDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.GetBookCommentsOkResponseDTO;
import dev.bookstore.creeper.demo.dto.CreateBookCommentRequestDTO;
import dev.bookstore.creeper.demo.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RequestMapping("/api/books")
@Controller
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
                return ResponseEntity.ok(
                    service.getAllBooks(q, page, pagesize)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookInfo(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.ok(new BookDTO(service.getBookInfo(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> getBookComments(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.ok(new GetBookCommentsOkResponseDTO(service.getBookComments(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Object> createBotComment(
            @PathVariable Integer id,
            @RequestBody CreateBookCommentRequestDTO dto
        ) {
                try {
                    service.createBookComment(id, dto.getContent());
                    return ResponseEntity.ok(new GeneralResponseDTO(true, "Comment created successfully"));
                } catch (NoSuchElementException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
                }
        }
}
