package dev.bookstore.creeper.demo.service;

import javax.naming.AuthenticationException;

import dev.bookstore.creeper.demo.dto.CartItemDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;

public interface CartService {

    /**
     * @brief 获取购物车中的所有商品
     * @param userId 用户id
     * @return GetCartItemsOkDTO
     * @throws AuthenticationException
     */
    GetItemsOkDTO<CartItemDTO> getCartItems(int userId); 

    /**
     * @brief 添加书到购物车
     * @param int 用户id
     * @param bookId 书的id
     * @return 新的购物车项的id
     */
    Integer createCartItem(int userId, Integer bookId);

        /**
         * @brief 更新购物车中的商品数量
         * @param userId 用户id
         * @param cartItemId 购物车项id
         * @param number 新的数量
         * @throws AuthenticationException
         */
    void updateCartItem(int userId, Integer cartItemId, Integer number) throws AuthenticationException;

    /**
     * @brief 删除购物车中的商品
     * @param userId 用户id
     * @param cartItemId 购物车项id
     * @throws AuthenticationException
     */
    void deleteCartItem(int userId, Integer cartItemId) throws AuthenticationException;
}
