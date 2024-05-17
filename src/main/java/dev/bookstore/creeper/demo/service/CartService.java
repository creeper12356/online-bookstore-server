package dev.bookstore.creeper.demo.service;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.CartItemDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;

public interface CartService {

    /**
     * @brief 获取购物车中的所有商品
     * @param token 用户token
     * @return GetCartItemsOkDTO
     * @throws AuthenticationException
     */
    GetItemsOkDTO<CartItemDTO> getCartItems(String token) 
        throws AuthenticationException;

    /**
     * @brief 添加书到购物车
     * @param token 用户token
     * @param bookId 书的id
     * @return 新的购物车项的id
     * @throws AuthenticationException
     */
    Integer createCartItem(String token, Integer bookId) 
        throws AuthenticationException;

        /**
         * @brief 更新购物车中的商品数量
         * @param token 用户token
         * @param id 购物车项id
         * @param number 新的数量
         * @throws AuthenticationException
         */
    void updateCartItem(String token, Integer id, Integer number) 
        throws AuthenticationException;

    void deleteCartItem(String token, Integer id) 
        throws AuthenticationException;
}
