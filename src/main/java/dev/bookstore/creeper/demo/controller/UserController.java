package dev.bookstore.creeper.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
