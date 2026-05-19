package com.tcmmall.service;

import com.tcmmall.entity.Role;

import java.util.List;

public interface RoleService {
    int add(Role role);
    int update(Role role);
    int delete(Long id);
    Role getById(Long id);
    List<Role> list(String keyword);
    int assignPermissions(Long roleId, List<Long> permissionIds);
}
