package dev.bookstore.creeper.demo.service;

import dev.bookstore.creeper.demo.model.User;

public interface UserService {
    /**
     * @brief 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    User getMe(int userId);
}
