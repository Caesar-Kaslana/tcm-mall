package com.tcmmall.service;

import com.tcmmall.entity.Goods;
import com.tcmmall.vo.PageResult;

import java.util.List;

public interface GoodsService {
    int add(Goods goods);
    int update(Goods goods);
    int delete(Long id);
    Goods getById(Long id);
    PageResult<Goods> page(Long categoryId, String keyword, Integer pageNum, Integer pageSize);
    List<Goods> list(Long categoryId, String keyword, Integer status);
    int updateStatus(Long id, Integer status);
    List<Goods> lowStock(Integer threshold);
}
