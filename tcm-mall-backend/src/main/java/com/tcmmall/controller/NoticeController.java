package com.tcmmall.controller;

import com.tcmmall.entity.Notice;
import com.tcmmall.mapper.NoticeMapper;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/notice/list")
    public Result<List<Notice>> list() {
        return Result.success(noticeMapper.selectList(1));
    }

    @GetMapping("/admin/notice/list")
    public Result<List<Notice>> adminList() {
        return Result.success(noticeMapper.selectList(null));
    }

    @PostMapping("/admin/notice/add")
    public Result<Void> add(@RequestBody Notice notice) {
        noticeMapper.insert(notice);
        return Result.success();
    }

    @PutMapping("/admin/notice/update")
    public Result<Void> update(@RequestBody Notice notice) {
        noticeMapper.update(notice);
        return Result.success();
    }

    @DeleteMapping("/admin/notice/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeMapper.deleteById(id);
        return Result.success();
    }
}
