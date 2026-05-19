package com.tcmmall.mapper;

import com.tcmmall.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole userRole);
    int deleteByUserId(Long userId);
    int deleteByRoleId(Long roleId);
    List<UserRole> selectByUserId(Long userId);
    List<Long> selectRoleIdsByUserId(Long userId);
    int batchInsert(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
