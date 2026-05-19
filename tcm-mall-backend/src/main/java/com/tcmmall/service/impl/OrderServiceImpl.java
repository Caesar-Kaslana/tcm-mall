package com.tcmmall.service.impl;

import com.tcmmall.entity.*;
import com.tcmmall.mapper.*;
import com.tcmmall.service.OrderService;
import com.tcmmall.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    @Transactional
    public Order createOrder(Long userId, Long addressId, String remark, List<Long> cartIds) {
        List<Cart> carts;
        if (cartIds != null && !cartIds.isEmpty()) {
            carts = new ArrayList<>();
            for (Long cartId : cartIds) {
                Cart cart = cartMapper.selectById(cartId);
                if (cart != null && cart.getUserId().equals(userId)) {
                    carts.add(cart);
                }
            }
        } else {
            carts = cartMapper.selectByUserId(userId);
        }
        if (carts == null || carts.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new RuntimeException("收货地址不存在");
        }
        for (Cart cart : carts) {
            Goods goods = goodsMapper.selectById(cart.getGoodsId());
            if (goods == null || goods.getStatus() != 1) {
                throw new RuntimeException("商品" + cart.getGoodsName() + "已下架");
            }
            if (goods.getStock() < cart.getQuantity()) {
                throw new RuntimeException("商品" + goods.getName() + "库存不足");
            }
        }
        String orderNo = "TCM" + System.currentTimeMillis();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderDetail> details = new ArrayList<>();
        List<Long> idsToDelete = new ArrayList<>();
        for (Cart cart : carts) {
            BigDecimal subtotal = cart.getGoodsPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
            totalAmount = totalAmount.add(subtotal);
            OrderDetail detail = new OrderDetail();
            detail.setGoodsId(cart.getGoodsId());
            detail.setGoodsName(cart.getGoodsName());
            detail.setGoodsCover(cart.getGoodsCover());
            detail.setGoodsPrice(cart.getGoodsPrice());
            detail.setQuantity(cart.getQuantity());
            detail.setSubtotal(subtotal);
            details.add(detail);
            idsToDelete.add(cart.getId());
        }
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setReceiverName(address.getReceiverName());
        order.setReceiverPhone(address.getReceiverPhone());
        order.setReceiverAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        order.setTotalAmount(totalAmount);
        order.setStatus(0);
        orderMapper.insert(order);
        for (OrderDetail detail : details) {
            detail.setOrderId(order.getId());
        }
        orderDetailMapper.batchInsert(details);
        for (Cart cart : carts) {
            Goods goods = goodsMapper.selectById(cart.getGoodsId());
            goodsMapper.updateStock(goods.getId(), goods.getStock() - cart.getQuantity());
            goodsMapper.updateSales(goods.getId(), goods.getSales() + cart.getQuantity());
        }
        cartMapper.deleteBatch(idsToDelete);
        return orderMapper.selectById(order.getId());
    }

    @Override
    public int payOrder(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) throw new RuntimeException("订单不存在");
        if (order.getStatus() != 0) throw new RuntimeException("订单状态异常");
        Order update = new Order();
        update.setId(order.getId());
        update.setStatus(1);
        update.setPayTime(LocalDateTime.now());
        return orderMapper.update(update);
    }

    @Override
    public int shipOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) throw new RuntimeException("订单不存在");
        if (order.getStatus() != 1) throw new RuntimeException("订单状态异常");
        Order update = new Order();
        update.setId(id);
        update.setStatus(2);
        update.setShipTime(LocalDateTime.now());
        return orderMapper.update(update);
    }

    @Override
    public int receiveOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) throw new RuntimeException("订单不存在");
        if (order.getStatus() != 2) throw new RuntimeException("订单状态异常");
        Order update = new Order();
        update.setId(id);
        update.setStatus(3);
        update.setReceiveTime(LocalDateTime.now());
        return orderMapper.update(update);
    }

    @Override
    @Transactional
    public int cancelOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) throw new RuntimeException("订单不存在");
        if (order.getStatus() != 0) throw new RuntimeException("只能取消待付款订单");
        List<OrderDetail> details = orderDetailMapper.selectByOrderId(id);
        for (OrderDetail detail : details) {
            Goods goods = goodsMapper.selectById(detail.getGoodsId());
            if (goods != null) {
                goodsMapper.updateStock(goods.getId(), goods.getStock() + detail.getQuantity());
                goodsMapper.updateSales(goods.getId(), goods.getSales() - detail.getQuantity());
            }
        }
        Order update = new Order();
        update.setId(id);
        update.setStatus(4);
        return orderMapper.update(update);
    }

    @Override
    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> listByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public PageResult<Order> page(String orderNo, Integer status, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Order> list = orderMapper.selectList(orderNo, status, offset, pageSize);
        Long total = orderMapper.selectCount(orderNo, status);
        return new PageResult<>(total, list);
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> map = new HashMap<>();
        map.put("recentOrders", orderMapper.selectRecent(5));
        return map;
    }
}
