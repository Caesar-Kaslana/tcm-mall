package com.tcmmall.service.impl;

import com.tcmmall.entity.Permission;
import com.tcmmall.mapper.PermissionMapper;
import com.tcmmall.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int add(Permission permission) {
        permission.setStatus(1);
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Permission permission) {
        return permissionMapper.update(permission);
    }

    @Override
    public int delete(Long id) {
        return permissionMapper.deleteById(id);
    }

    @Override
    public Permission getById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public List<Permission> list(Integer type) {
        return permissionMapper.selectList(type);
    }

    @Override
    public List<Permission> getByRoleId(Long roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }
}
