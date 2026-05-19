package com.tcmmall.service;

import com.tcmmall.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> login(String username, String password, HttpSession session);
    int register(User user);
    int update(User user);
    int updatePassword(Long userId, String oldPassword, String newPassword);
    User getById(Long id);
    List<User> list(String keyword);
}
