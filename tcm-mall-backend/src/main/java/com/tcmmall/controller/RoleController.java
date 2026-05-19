package com.tcmmall.controller;

import com.tcmmall.entity.Role;
import com.tcmmall.service.RoleService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Result<List<Role>> list(@RequestParam(required = false) String keyword) {
        return Result.success(roleService.list(keyword));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Role role) {
        roleService.add(role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Role role) {
        roleService.update(role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Role> detail(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }

    @PostMapping("/assignPermission")
    public Result<Void> assignPermission(@RequestBody Map<String, Object> params) {
        Long roleId = Long.valueOf(params.get("roleId").toString());
        List<Long> permissionIds = (List<Long>) params.get("permissionIds");
        roleService.assignPermissions(roleId, permissionIds);
        return Result.success();
    }
}
