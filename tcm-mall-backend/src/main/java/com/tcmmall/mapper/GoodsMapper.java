package com.tcmmall.mapper;

import com.tcmmall.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    int insert(Goods goods);
    int update(Goods goods);
    int deleteById(Long id);
    Goods selectById(Long id);
    List<Goods> selectList(@Param("categoryId") Long categoryId, @Param("keyword") String keyword, @Param("status") Integer status);
    List<Goods> selectPage(@Param("categoryId") Long categoryId, @Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);
    Long selectCount(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    int updateSales(@Param("id") Long id, @Param("sales") Integer sales);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    List<Goods> selectLowStock(@Param("threshold") Integer threshold);
}
