package dev.bookstore.creeper.demo.utils;

import javax.naming.AuthenticationException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import dev.bookstore.creeper.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    public static void setSession(User user) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
        }
    }

    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getSession(false);
        }
        return null;
    }

    public static Integer getSessionUserId() throws AuthenticationException {
        try {
            return Integer.parseInt(getSession().getAttribute("userId").toString());
        } catch(NullPointerException e) {
            throw new AuthenticationException("User not logged in.");
        }
    }
}
