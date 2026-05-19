package com.tcmmall.mapper;

import com.tcmmall.entity.Purchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseMapper {
    int insert(Purchase purchase);
    List<Purchase> selectList(@Param("goodsName") String goodsName, @Param("offset") Integer offset, @Param("limit") Integer limit);
    Long selectCount(@Param("goodsName") String goodsName);
    Purchase selectById(Long id);
}
