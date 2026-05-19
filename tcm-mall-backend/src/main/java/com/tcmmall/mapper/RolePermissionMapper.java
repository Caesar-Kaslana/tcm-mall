package com.tcmmall.mapper;

import com.tcmmall.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    int insert(RolePermission rolePermission);
    int deleteByRoleId(Long roleId);
    List<RolePermission> selectByRoleId(Long roleId);
    int batchInsert(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
