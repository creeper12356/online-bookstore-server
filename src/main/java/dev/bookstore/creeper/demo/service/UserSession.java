package dev.bookstore.creeper.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
    private LocalDateTime startTime;

    public void start() {
        this.startTime = LocalDateTime.now();
    }

    public Duration stop() {
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        this.startTime = null;
        return duration;
    }
    
}
