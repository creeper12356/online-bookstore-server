package dev.bookstore.creeper.demo.service;

import java.util.Date;
import java.util.List;

import dev.bookstore.creeper.demo.dto.BookSalesDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.UserPurchaseDTO;
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

    /**
     * @brief 获取用户消费排行
     * @param currentUserId 当前用户id
     * @param from 
     * @param to
     * @param maxcount
     * @return
     * @throws Exception
     */
    GetItemsOkDTO<UserPurchaseDTO> getUserRank(Integer currentUserId, Date from, Date to, Integer maxcount) throws Exception;


    /**
     * @brief 获取用户在一定时间内消费的统计数据
     * @param currentUserId
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    GetItemsOkDTO<BookSalesDTO> getUserStatistic(Integer currentUserId, Date from, Date to) throws Exception;
}
