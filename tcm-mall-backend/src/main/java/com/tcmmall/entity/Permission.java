package com.tcmmall.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Permission {
    private Long id;
    private String name;
    private String code;
    private String url;
    private Integer type;
    private Long parentId;
    private Integer sort;
    private String icon;
    private Integer status;
    private LocalDateTime createTime;
}
