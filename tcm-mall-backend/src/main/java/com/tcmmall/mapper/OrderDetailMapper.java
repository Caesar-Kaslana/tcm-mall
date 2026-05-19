package com.tcmmall.mapper;

import com.tcmmall.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    int insert(OrderDetail orderDetail);
    int batchInsert(@Param("details") List<OrderDetail> details);
    List<OrderDetail> selectByOrderId(Long orderId);
}
