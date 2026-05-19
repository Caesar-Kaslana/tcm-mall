package com.tcmmall.service;

import com.tcmmall.entity.Purchase;
import com.tcmmall.vo.PageResult;

public interface PurchaseService {
    int add(Purchase purchase);
    PageResult<Purchase> page(String goodsName, Integer pageNum, Integer pageSize);
    Purchase getById(Long id);
}
