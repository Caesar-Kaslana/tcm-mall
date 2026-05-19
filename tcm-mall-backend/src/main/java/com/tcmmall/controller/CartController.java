package com.tcmmall.controller;

import com.tcmmall.entity.Cart;
import com.tcmmall.entity.User;
import com.tcmmall.service.CartService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public Result<List<Cart>> list(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(cartService.list(user.getId()));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Cart cart, HttpSession session) {
        User user = (User) session.getAttribute("user");
        cart.setUserId(user.getId());
        cartService.add(cart);
        return Result.success();
    }

    @PutMapping("/update/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        cartService.updateQuantity(id, params.get("quantity"));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        cartService.delete(id);
        return Result.success();
    }
}
