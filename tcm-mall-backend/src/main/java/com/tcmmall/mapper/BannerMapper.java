package com.tcmmall.mapper;

import com.tcmmall.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    int insert(Banner banner);
    int update(Banner banner);
    int deleteById(Long id);
    Banner selectById(Long id);
    List<Banner> selectList(@Param("status") Integer status);
}
