package dev.bookstore.creeper.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.BookSalesDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.UserProfileDTO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.UserService;
import dev.bookstore.creeper.demo.utils.SessionUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        // 配置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe() {
        try {
            return ResponseEntity
                    .ok(new UserProfileDTO(userService.getUserProfile(SessionUtils.getSessionUserId())));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserProfile(@PathVariable Integer id) {
        try {
            return ResponseEntity
                    .ok(new UserProfileDTO(userService.getUserProfile(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers(SessionUtils.getSessionUserId());
            List<UserProfileDTO> userProfiles = users.stream()
                    .map(user -> new UserProfileDTO(user))
                    .toList();
            return ResponseEntity
                    .ok(new GetItemsOkDTO<>(userProfiles.size(), userProfiles));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/ban")
    public ResponseEntity<Object> banUser(@PathVariable Integer userId) {
        try {
            userService.banUser(SessionUtils.getSessionUserId(), userId);
            return ResponseEntity
                    .ok(new GeneralResponseDTO(true, "User banned"));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/unban")
    public ResponseEntity<Object> unbanUser(@PathVariable Integer userId) {
        try {
            userService.unbanUser(SessionUtils.getSessionUserId(), userId);
            return ResponseEntity
                    .ok(new GeneralResponseDTO(true, "User unbanned"));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/rank")
    public ResponseEntity<Object> getUserRank(
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(defaultValue = "10") Integer maxcount) {
                try {
                    return ResponseEntity.ok(
                        userService.getUserRank(
                            SessionUtils.getSessionUserId(), 
                            from, 
                            to, 
                            maxcount
                        ));
                } catch(AuthenticationException e) {
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(new GeneralResponseDTO(false, e.getMessage()));
                } catch(Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(new GeneralResponseDTO(false, e.getMessage()));
                }
    }

    @GetMapping("/statistic")
    public GetItemsOkDTO<BookSalesDTO> getUserStatistic(
        @RequestParam(required = false) Date from,
        @RequestParam(required = false) Date to
    ) {
        try {
            return userService.getUserStatistic(
                SessionUtils.getSessionUserId(),
                from,
                to
            );
        } catch(AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
