package dev.bookstore.creeper.demo.service;

import java.util.List;

import dev.bookstore.creeper.demo.model.User;

public interface UserService {
    /**
     * @brief 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserProfile(int userId);

    /**
     * @brief 获取所有用户
     * @return 所有用户
     */
    List<User> getAllUsers(int currentUserId) throws Exception;

    /**
     * @brief 封禁用户
     * @param currentUserId 当前用户id
     * @param targetUserId 目标用户id
     */
    void banUser(int currentUserId, int targetUserId) throws Exception;

    /**
     * @brief 解封用户
     * @param currentUserId 当前用户id
     * @param targetUserId 目标用户id
     */
    void unbanUser(int currentUserId, int targetUserId) throws Exception;
}
