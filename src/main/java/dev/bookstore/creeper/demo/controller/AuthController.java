package dev.bookstore.creeper.demo.controller;

import java.time.Duration;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.dto.LogoutOkResponseDTO;
import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.AuthService;
import dev.bookstore.creeper.demo.utils.SessionUtils;

@RestController
@RequestMapping("/api/auth")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO dto) {
        try {
            authService.register(dto);
            return ResponseEntity
                    .ok(new GeneralResponseDTO(true, "User registered successfully"));
        }
        
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
        @RequestBody RegisterRequestDTO dto
    ) {
        try {
            User loginUser = authService.login(dto);
            SessionUtils.setSession(loginUser);
            authService.startSession();
            return ResponseEntity
                    .ok(new GeneralResponseDTO(true, "Login successful"));
        } catch(AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<Object> logout() {
        Duration sessionDuration = authService.stopSession();
        SessionUtils.getSession().invalidate();
        return ResponseEntity.ok(new LogoutOkResponseDTO(sessionDuration.toSeconds()));
    }
}
