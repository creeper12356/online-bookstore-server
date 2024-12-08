package dev.bookstore.creeper.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.BookDTO;
import dev.bookstore.creeper.demo.dto.CreateBookCommentRequestDTO;
import dev.bookstore.creeper.demo.dto.CreateBookOkResponseDTO;
import dev.bookstore.creeper.demo.dto.CreateTagRelationshipRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GetBookCommentsOkResponseDTO;
import dev.bookstore.creeper.demo.dto.UpdateBookInfoDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.service.BookService;
import dev.bookstore.creeper.demo.service.BookTagService;
import dev.bookstore.creeper.demo.utils.SessionUtils;

@RequestMapping("/api/books")
@RestController
public class BookController {
    private final BookService service;

    @Autowired
    private BookTagService bookTagService;

    public BookController(BookService service) {
        this.service = service;
    }

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        // 配置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        return ResponseEntity.ok(
                service.getAllBooks(q, page, pagesize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookInfo(
            @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new BookDTO(service.getBookInfo(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createBook(
            @RequestBody UpdateBookInfoDTO dto) {
        try {
            Integer newBookId = service.createBook(SessionUtils.getSessionUserId(), dto);
            return ResponseEntity.ok(
                    new CreateBookOkResponseDTO(
                            true,
                            "Book created successfully",
                            newBookId));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBookInfo(
            @PathVariable Integer id,
            @RequestBody UpdateBookInfoDTO dto) {
        try {
            service.updateBookInfo(SessionUtils.getSessionUserId(), id, dto);
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Book updated successfully"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(
            @PathVariable Integer id) {
        try {
            service.deleteBook(SessionUtils.getSessionUserId(), id);
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Book deleted successfully"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> getBookComments(
            @PathVariable Integer id) {
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
            @RequestBody CreateBookCommentRequestDTO dto) {
        try {
            service.createBookComment(id, dto.getContent());
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Comment created successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/rank")
    public ResponseEntity<Object> getBookRank(
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(defaultValue = "10") Integer maxcount) {
        try {
            return ResponseEntity.ok(service.getBookRank(SessionUtils.getSessionUserId(), from, to, maxcount));
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDTO(false, e.getMessage()));
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<Object> getSimilarBooks(
        @PathVariable Integer id) {
            try {
                List<Book> books = service.getSimilarBooks(id);
                return ResponseEntity.ok(new GetAllBooksOkResponseDTO(books.size(), books));
            } catch(Exception e) {
                return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, e.getMessage()));
            }
        }
    
    @PutMapping("/{id}/tags")
    public ResponseEntity<Object> updateBookTags(
        @PathVariable Integer id, 
        @RequestBody List<String> tags) {
        try {
            service.updateBookTags(id, tags);
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Tags updated successfully. New tags: " + tags));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/tags/relationship")
    public ResponseEntity<Object> createTagRelationship(@RequestBody CreateTagRelationshipRequestDTO dto) {
        try {
            bookTagService.createRelationBetween(dto.getTag1(), dto.getTag2());
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Tag relationship between " + dto.getTag1() + " and " + dto.getTag2() + " created successfully"));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/tags/relationship")
    public ResponseEntity<Object> removeTagRelationship(@RequestBody CreateTagRelationshipRequestDTO dto) {
        try {
            bookTagService.removeRelationshipBetween(dto.getTag1(), dto.getTag2());
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Tag relationship between " + dto.getTag1() + " and " + dto.getTag2() + " deleted successfully"));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/tags")
    public ResponseEntity<Object> createTag(@RequestBody String tag) {
        try {
            bookTagService.createTag(tag);
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Tag " + tag + " created successfully"));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

}
