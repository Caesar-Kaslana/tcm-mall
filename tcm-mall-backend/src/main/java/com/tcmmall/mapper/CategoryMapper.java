package com.tcmmall.mapper;

import com.tcmmall.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int insert(Category category);
    int update(Category category);
    int deleteById(Long id);
    Category selectById(Long id);
    List<Category> selectList(@Param("parentId") Long parentId);
    List<Category> selectAll();
}
