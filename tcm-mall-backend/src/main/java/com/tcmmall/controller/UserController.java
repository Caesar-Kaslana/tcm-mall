package com.tcmmall.controller;

import com.tcmmall.entity.User;
import com.tcmmall.service.UserService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params, HttpSession session) {
        Map<String, Object> data = userService.login(params.get("username"), params.get("password"), session);
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> info(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(userService.getById(user.getId()));
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody User user, HttpSession session) {
        User current = (User) session.getAttribute("user");
        user.setId(current.getId());
        userService.update(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Void> password(@RequestBody Map<String, String> params, HttpSession session) {
        User current = (User) session.getAttribute("user");
        userService.updatePassword(current.getId(), params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String keyword) {
        return Result.success(userService.list(keyword));
    }
}
