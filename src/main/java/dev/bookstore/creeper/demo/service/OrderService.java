package dev.bookstore.creeper.demo.service;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;

public interface OrderService {

    /**
     * @brief 获取所有订单
     * @param userId 当前用户id
     * @return 
     */
    GetItemsOkDTO<OrderDTO> getOrders(int userId);

    /**
     * @brief 创建订单
     * @param userId 当前用户id
     * @param dto 创建订单的请求体
     * @throws AuthenticationException 创建订单失败，抛出异常
     */
    void createOrder(int userId, CreateOrderRequestDTO dto); 
}
