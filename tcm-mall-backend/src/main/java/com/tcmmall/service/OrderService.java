package com.tcmmall.service;

import com.tcmmall.entity.Order;
import com.tcmmall.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(Long userId, Long addressId, String remark, List<Long> cartIds);
    int payOrder(String orderNo);
    int shipOrder(Long id);
    int receiveOrder(Long id);
    int cancelOrder(Long id);
    Order getById(Long id);
    List<Order> listByUserId(Long userId);
    PageResult<Order> page(String orderNo, Integer status, Integer pageNum, Integer pageSize);
    Map<String, Object> statistics();
}
