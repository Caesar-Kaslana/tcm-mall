package com.tcmmall.service.impl;

import com.tcmmall.entity.Goods;
import com.tcmmall.mapper.GoodsMapper;
import com.tcmmall.service.GoodsService;
import com.tcmmall.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int add(Goods goods) {
        goods.setStatus(1);
        goods.setSales(0);
        return goodsMapper.insert(goods);
    }

    @Override
    public int update(Goods goods) {
        return goodsMapper.update(goods);
    }

    @Override
    public int delete(Long id) {
        return goodsMapper.deleteById(id);
    }

    @Override
    public Goods getById(Long id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public PageResult<Goods> page(Long categoryId, String keyword, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Goods> list = goodsMapper.selectPage(categoryId, keyword, offset, pageSize);
        Long total = goodsMapper.selectCount(categoryId, keyword);
        return new PageResult<>(total, list);
    }

    @Override
    public List<Goods> list(Long categoryId, String keyword, Integer status) {
        return goodsMapper.selectList(categoryId, keyword, status);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return goodsMapper.updateStatus(id, status);
    }

    @Override
    public List<Goods> lowStock(Integer threshold) {
        return goodsMapper.selectLowStock(threshold);
    }
}
