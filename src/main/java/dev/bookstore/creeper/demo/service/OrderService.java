package dev.bookstore.creeper.demo.service;

import java.sql.Date;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;

public interface OrderService {

    /**
     * @brief 获取所有订单
     * @param userId 当前用户id
     * @param query 查询关键字，为""表示不进行筛选
     * @param from 订单创建时间下界，为null表示无下界
     * @param to 订单创建时间上界，为null表示无上界
     * @return 
     */
    GetItemsOkDTO<OrderDTO> getOrders(int userId, String query, Date from, Date to);

    /**
     * @brief 创建订单
     * @param userId 当前用户id
     * @param dto 创建订单的请求体
     * @throws AuthenticationException 创建订单失败，抛出异常
     */
    void createOrder(int userId, CreateOrderRequestDTO dto); 
}
