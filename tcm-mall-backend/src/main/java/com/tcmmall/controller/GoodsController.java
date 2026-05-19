package com.tcmmall.controller;

import com.tcmmall.entity.Goods;
import com.tcmmall.service.GoodsService;
import com.tcmmall.vo.PageResult;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods/list")
    public Result<PageResult<Goods>> list(@RequestParam(required = false) Long categoryId,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(goodsService.page(categoryId, keyword, pageNum, pageSize));
    }

    @GetMapping("/goods/detail/{id}")
    public Result<Goods> detail(@PathVariable Long id) {
        return Result.success(goodsService.getById(id));
    }

    @GetMapping("/admin/goods/list")
    public Result<PageResult<Goods>> adminList(@RequestParam(required = false) Long categoryId,
                                                  @RequestParam(required = false) String keyword,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(goodsService.page(categoryId, keyword, pageNum, pageSize));
    }

    @PostMapping("/admin/goods/add")
    public Result<Void> add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }

    @PutMapping("/admin/goods/update")
    public Result<Void> update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return Result.success();
    }

    @DeleteMapping("/admin/goods/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        goodsService.delete(id);
        return Result.success();
    }

    @PutMapping("/admin/goods/status/{id}")
    public Result<Void> status(@PathVariable Long id, @RequestParam Integer status) {
        goodsService.updateStatus(id, status);
        return Result.success();
    }

    @GetMapping("/admin/goods/lowStock")
    public Result<List<Goods>> lowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return Result.success(goodsService.lowStock(threshold));
    }
}
