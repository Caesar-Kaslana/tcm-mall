package com.tcmmall.service.impl;

import com.tcmmall.entity.Goods;
import com.tcmmall.entity.Purchase;
import com.tcmmall.mapper.GoodsMapper;
import com.tcmmall.mapper.PurchaseMapper;
import com.tcmmall.service.PurchaseService;
import com.tcmmall.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public int add(Purchase purchase) {
        Goods goods = goodsMapper.selectById(purchase.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        String purchaseNo = "CG" + System.currentTimeMillis();
        purchase.setPurchaseNo(purchaseNo);
        purchase.setGoodsName(goods.getName());
        purchase.setTotalPrice(purchase.getUnitPrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
        int rows = purchaseMapper.insert(purchase);
        goodsMapper.updateStock(goods.getId(), goods.getStock() + purchase.getQuantity());
        return rows;
    }

    @Override
    public PageResult<Purchase> page(String goodsName, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Purchase> list = purchaseMapper.selectList(goodsName, offset, pageSize);
        Long total = purchaseMapper.selectCount(goodsName);
        return new PageResult<>(total, list);
    }

    @Override
    public Purchase getById(Long id) {
        return purchaseMapper.selectById(id);
    }
}
