package dev.bookstore.creeper.demo.service;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.RegisterRequestDTO;
import dev.bookstore.creeper.demo.model.User;

public interface AuthService {
    /**
     * @brief 注册用户
     * @param requestDTO 注册的请求体
     * @throws IllegalArgumentException
     */
    void register(RegisterRequestDTO requestDTO) throws IllegalArgumentException;
    /**
     * @brief 登录用户
     * @param requestDTO 登录的请求体
     * @return 登录成功的用户
     * @throws AuthenticationException 登录失败，抛出异常
     */
    User login(RegisterRequestDTO requestDTO) throws AuthenticationException;
}
