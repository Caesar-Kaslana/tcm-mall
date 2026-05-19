package com.tcmmall.mapper;

import com.tcmmall.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int insert(Order order);
    int update(Order order);
    Order selectById(Long id);
    Order selectByOrderNo(String orderNo);
    List<Order> selectByUserId(Long userId);
    List<Order> selectList(@Param("orderNo") String orderNo, @Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit);
    Long selectCount(@Param("orderNo") String orderNo, @Param("status") Integer status);
    List<Order> selectRecent(@Param("limit") Integer limit);
}
