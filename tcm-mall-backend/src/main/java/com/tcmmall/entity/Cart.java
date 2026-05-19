package com.tcmmall.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Cart {
    private Long id;
    private Long userId;
    private Long goodsId;
    private String goodsName;
    private String goodsCover;
    private BigDecimal goodsPrice;
    private Integer quantity;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
