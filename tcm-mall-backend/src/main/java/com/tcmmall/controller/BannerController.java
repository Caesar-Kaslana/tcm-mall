package com.tcmmall.controller;

import com.tcmmall.entity.Banner;
import com.tcmmall.mapper.BannerMapper;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BannerController {

    @Autowired
    private BannerMapper bannerMapper;

    @GetMapping("/banner/list")
    public Result<List<Banner>> list() {
        return Result.success(bannerMapper.selectList(1));
    }

    @GetMapping("/admin/banner/list")
    public Result<List<Banner>> adminList() {
        return Result.success(bannerMapper.selectList(null));
    }

    @PostMapping("/admin/banner/add")
    public Result<Void> add(@RequestBody Banner banner) {
        bannerMapper.insert(banner);
        return Result.success();
    }

    @PutMapping("/admin/banner/update")
    public Result<Void> update(@RequestBody Banner banner) {
        bannerMapper.update(banner);
        return Result.success();
    }

    @DeleteMapping("/admin/banner/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerMapper.deleteById(id);
        return Result.success();
    }
}
