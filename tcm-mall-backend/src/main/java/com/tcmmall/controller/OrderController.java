package com.tcmmall.controller;

import com.tcmmall.entity.Order;
import com.tcmmall.entity.User;
import com.tcmmall.service.OrderService;
import com.tcmmall.vo.PageResult;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public Result<Order> create(@RequestBody Map<String, Object> params, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long addressId = Long.valueOf(params.get("addressId").toString());
        String remark = params.get("remark") != null ? params.get("remark").toString() : "";
        List<?> cartIdsRaw = params.get("cartIds") != null ? (List<?>) params.get("cartIds") : null;
        List<Long> cartIds = null;
        if (cartIdsRaw != null) {
            cartIds = new ArrayList<>();
            for (Object o : cartIdsRaw) {
                cartIds.add(Long.valueOf(o.toString()));
            }
        }
        return Result.success(orderService.createOrder(user.getId(), addressId, remark, cartIds));
    }

    @GetMapping("/order/list")
    public Result<List<Order>> list(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(orderService.listByUserId(user.getId()));
    }

    @GetMapping("/order/detail/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @PostMapping("/order/pay")
    public Result<Void> pay(@RequestBody Map<String, String> params) {
        orderService.payOrder(params.get("orderNo"));
        return Result.success();
    }

    @PutMapping("/order/receive/{id}")
    public Result<Void> receive(@PathVariable Long id) {
        orderService.receiveOrder(id);
        return Result.success();
    }

    @PutMapping("/order/cancel/{id}")
    public Result<Void> cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }

    @GetMapping("/admin/order/list")
    public Result<PageResult<Order>> adminList(@RequestParam(required = false) String orderNo,
                                                @RequestParam(required = false) Integer status,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(orderService.page(orderNo, status, pageNum, pageSize));
    }

    @PutMapping("/admin/order/ship/{id}")
    public Result<Void> ship(@PathVariable Long id) {
        orderService.shipOrder(id);
        return Result.success();
    }

    @GetMapping("/admin/order/statistics")
    public Result<Map<String, Object>> statistics() {
        return Result.success(orderService.statistics());
    }
}
