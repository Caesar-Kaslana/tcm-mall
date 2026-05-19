package com.tcmmall.service.impl;

import com.tcmmall.entity.Permission;
import com.tcmmall.entity.Role;
import com.tcmmall.entity.User;
import com.tcmmall.entity.UserRole;
import com.tcmmall.mapper.PermissionMapper;
import com.tcmmall.mapper.RoleMapper;
import com.tcmmall.mapper.UserMapper;
import com.tcmmall.mapper.UserRoleMapper;
import com.tcmmall.service.UserService;
import com.tcmmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Map<String, Object> login(String username, String password, HttpSession session) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (!MD5Util.encrypt(password).equals(user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        List<Role> roles = roleMapper.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = roleIds.isEmpty() ? Collections.emptyList() : permissionMapper.selectByRoleIds(roleIds);
        List<String> urls = permissions.stream().map(Permission::getUrl).filter(Objects::nonNull).collect(Collectors.toList());
        session.setAttribute("user", user);
        session.setAttribute("permissions", urls);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("roles", roles);
        result.put("permissions", permissions);
        return result;
    }

    @Override
    @Transactional
    public int register(User user) {
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setStatus(1);
        int rows = userMapper.insert(user);
        // 为新注册用户分配"普通用户"角色 (role_id = 4)
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(4L);
        userRoleMapper.insert(userRole);
        return rows;
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!MD5Util.encrypt(oldPassword).equals(user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        User update = new User();
        update.setId(userId);
        update.setPassword(MD5Util.encrypt(newPassword));
        return userMapper.update(update);
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list(String keyword) {
        return userMapper.selectList(keyword);
    }
}
