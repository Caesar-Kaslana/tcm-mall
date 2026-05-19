package com.tcmmall.service;

import com.tcmmall.entity.Permission;

import java.util.List;

public interface PermissionService {
    int add(Permission permission);
    int update(Permission permission);
    int delete(Long id);
    Permission getById(Long id);
    List<Permission> list(Integer type);
    List<Permission> getByRoleId(Long roleId);
}
