package dev.bookstore.creeper.demo.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final HttpSession httpSession;

    public SessionInterceptor(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            // 通过预检请求
            return true;
        }
        
        if(httpSession.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

}