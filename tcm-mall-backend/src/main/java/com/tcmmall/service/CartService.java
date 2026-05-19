package com.tcmmall.service;

import com.tcmmall.entity.Cart;

import java.util.List;

public interface CartService {
    int add(Cart cart);
    int updateQuantity(Long id, Integer quantity);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    List<Cart> list(Long userId);
}
