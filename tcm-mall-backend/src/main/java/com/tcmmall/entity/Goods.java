package com.tcmmall.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Goods {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String cover;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private String description;
    private String effect;
    private String usage;
    private String contraindication;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
