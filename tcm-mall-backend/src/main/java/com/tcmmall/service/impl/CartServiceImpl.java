package com.tcmmall.service.impl;

import com.tcmmall.entity.Cart;
import com.tcmmall.entity.Goods;
import com.tcmmall.mapper.CartMapper;
import com.tcmmall.mapper.GoodsMapper;
import com.tcmmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int add(Cart cart) {
        Goods goods = goodsMapper.selectById(cart.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        Cart exist = cartMapper.selectByUserIdAndGoodsId(cart.getUserId(), cart.getGoodsId());
        if (exist != null) {
            return cartMapper.updateQuantity(exist.getId(), exist.getQuantity() + cart.getQuantity());
        }
        cart.setGoodsName(goods.getName());
        cart.setGoodsCover(goods.getCover());
        cart.setGoodsPrice(goods.getPrice());
        return cartMapper.insert(cart);
    }

    @Override
    public int updateQuantity(Long id, Integer quantity) {
        return cartMapper.updateQuantity(id, quantity);
    }

    @Override
    public int delete(Long id) {
        return cartMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return cartMapper.deleteBatch(ids);
    }

    @Override
    public List<Cart> list(Long userId) {
        return cartMapper.selectByUserId(userId);
    }
}
