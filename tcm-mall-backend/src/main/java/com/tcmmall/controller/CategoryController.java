package com.tcmmall.controller;

import com.tcmmall.entity.Category;
import com.tcmmall.mapper.CategoryMapper;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/goods/category/list")
    public Result<List<Category>> list(@RequestParam(required = false) Long parentId) {
        return Result.success(categoryMapper.selectList(parentId));
    }

    @GetMapping("/admin/category/all")
    public Result<List<Category>> all() {
        return Result.success(categoryMapper.selectAll());
    }

    @PostMapping("/admin/category/add")
    public Result<Void> add(@RequestBody Category category) {
        categoryMapper.insert(category);
        return Result.success();
    }

    @PutMapping("/admin/category/update")
    public Result<Void> update(@RequestBody Category category) {
        categoryMapper.update(category);
        return Result.success();
    }

    @DeleteMapping("/admin/category/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }
}
