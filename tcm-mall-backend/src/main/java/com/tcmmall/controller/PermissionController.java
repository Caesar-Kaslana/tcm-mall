package com.tcmmall.controller;

import com.tcmmall.entity.Permission;
import com.tcmmall.service.PermissionService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public Result<List<Permission>> list(@RequestParam(required = false) Integer type) {
        return Result.success(permissionService.list(type));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Permission permission) {
        permissionService.add(permission);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Permission permission) {
        permissionService.update(permission);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return Result.success();
    }

    @GetMapping("/role/{roleId}")
    public Result<List<Permission>> getByRoleId(@PathVariable Long roleId) {
        return Result.success(permissionService.getByRoleId(roleId));
    }
}
