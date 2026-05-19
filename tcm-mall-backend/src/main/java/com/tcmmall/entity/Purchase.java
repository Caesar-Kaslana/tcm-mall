package com.tcmmall.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Purchase {
    private Long id;
    private String purchaseNo;
    private Long goodsId;
    private String goodsName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String supplier;
    private String remark;
    private LocalDateTime createTime;
}
