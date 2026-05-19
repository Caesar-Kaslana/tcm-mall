package com.tcmmall.controller;

import com.tcmmall.entity.User;
import com.tcmmall.mapper.UserMapper;
import com.tcmmall.mapper.UserRoleMapper;
import com.tcmmall.service.OrderService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectList(null).size());
        map.put("recentOrders", orderService.statistics().get("recentOrders"));
        return Result.success(map);
    }

    @GetMapping("/user/list")
    public Result<List<User>> userList(@RequestParam(required = false) String keyword) {
        return Result.success(userMapper.selectList(keyword));
    }

    @PostMapping("/user/assignRole")
    public Result<Void> assignRole(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        List<?> rawRoleIds = (List<?>) params.get("roleIds");
        List<Long> roleIds = new ArrayList<>();
        if (rawRoleIds != null) {
            for (Object id : rawRoleIds) {
                roleIds.add(Long.valueOf(id.toString()));
            }
        }
        userRoleMapper.deleteByUserId(userId);
        if (!roleIds.isEmpty()) {
            userRoleMapper.batchInsert(userId, roleIds);
        }
        return Result.success();
    }
}
