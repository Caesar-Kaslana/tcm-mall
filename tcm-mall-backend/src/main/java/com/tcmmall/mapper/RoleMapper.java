package com.tcmmall.mapper;

import com.tcmmall.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int insert(Role role);
    int update(Role role);
    int deleteById(Long id);
    Role selectById(Long id);
    List<Role> selectList(@Param("keyword") String keyword);
    List<Role> selectByUserId(Long userId);
}
