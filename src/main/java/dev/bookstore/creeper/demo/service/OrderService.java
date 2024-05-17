package dev.bookstore.creeper.demo.service;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.OrderDTO;

public interface OrderService {

    /**
     * @brief 获取所有订单
     * @param token 用户token
     * @return 
     * @throws AuthenticationException
     */
    GetItemsOkDTO<OrderDTO> getOrders(String token) 
        throws AuthenticationException;

    void createOrder(String token, CreateOrderRequestDTO dto) 
        throws AuthenticationException;
}
