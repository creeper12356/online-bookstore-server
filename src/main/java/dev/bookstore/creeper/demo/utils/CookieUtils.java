package dev.bookstore.creeper.demo.utils;

import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        String cookieValue = String.format("%s=%s; HttpOnly; Max-Age=%s; Path=/; SameSite=None", name, value, maxAge);
        response.setHeader("Set-Cookie", cookieValue);
    }
}