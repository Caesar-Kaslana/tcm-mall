package com.tcmmall.mapper;

import com.tcmmall.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int insert(Cart cart);
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
    int deleteById(Long id);
    int deleteByUserId(Long userId);
    int deleteByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
    Cart selectById(Long id);
    Cart selectByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
    List<Cart> selectByUserId(Long userId);
    int deleteBatch(@Param("ids") List<Long> ids);
}
