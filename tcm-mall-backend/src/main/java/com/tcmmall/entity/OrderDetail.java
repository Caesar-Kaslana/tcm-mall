package com.tcmmall.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetail {
    private Long id;
    private Long orderId;
    private Long goodsId;
    private String goodsName;
    private String goodsCover;
    private BigDecimal goodsPrice;
    private Integer quantity;
    private BigDecimal subtotal;
}
