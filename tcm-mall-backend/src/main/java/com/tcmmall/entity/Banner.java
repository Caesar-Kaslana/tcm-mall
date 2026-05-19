package com.tcmmall.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Banner {
    private Long id;
    private String title;
    private String image;
    private String linkUrl;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
