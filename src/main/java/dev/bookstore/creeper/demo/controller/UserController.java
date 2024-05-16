package dev.bookstore.creeper.demo.controller;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.UserDTO;
import dev.bookstore.creeper.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(@CookieValue(value = "token", defaultValue = "") String token) {
        try {
            return ResponseEntity
                .ok(new UserDTO(userService.getMe(token)));
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
}
