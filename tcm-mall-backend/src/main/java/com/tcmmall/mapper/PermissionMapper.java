package com.tcmmall.mapper;

import com.tcmmall.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int insert(Permission permission);
    int update(Permission permission);
    int deleteById(Long id);
    Permission selectById(Long id);
    List<Permission> selectList(@Param("type") Integer type);
    List<Permission> selectByRoleId(Long roleId);
    List<Permission> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}
