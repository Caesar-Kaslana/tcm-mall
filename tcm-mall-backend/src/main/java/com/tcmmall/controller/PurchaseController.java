package com.tcmmall.controller;

import com.tcmmall.entity.Purchase;
import com.tcmmall.service.PurchaseService;
import com.tcmmall.vo.PageResult;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/list")
    public Result<PageResult<Purchase>> list(@RequestParam(required = false) String goodsName,
                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(purchaseService.page(goodsName, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Purchase purchase) {
        purchaseService.add(purchase);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Purchase> detail(@PathVariable Long id) {
        return Result.success(purchaseService.getById(id));
    }
}
