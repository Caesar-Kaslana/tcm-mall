package com.tcmmall.service.impl;

import com.tcmmall.entity.Role;
import com.tcmmall.mapper.RoleMapper;
import com.tcmmall.mapper.RolePermissionMapper;
import com.tcmmall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public int add(Role role) {
        role.setStatus(1);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        rolePermissionMapper.deleteByRoleId(id);
        return roleMapper.deleteById(id);
    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> list(String keyword) {
        return roleMapper.selectList(keyword);
    }

    @Override
    @Transactional
    public int assignPermissions(Long roleId, List<Long> permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
            return rolePermissionMapper.batchInsert(roleId, permissionIds);
        }
        return 1;
    }
}
