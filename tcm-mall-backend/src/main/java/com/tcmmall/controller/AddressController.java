package com.tcmmall.controller;

import com.tcmmall.entity.Address;
import com.tcmmall.entity.User;
import com.tcmmall.service.AddressService;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public Result<List<Address>> list(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(addressService.list(user.getId()));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Address address, HttpSession session) {
        User user = (User) session.getAttribute("user");
        address.setUserId(user.getId());
        addressService.add(address);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Address address, HttpSession session) {
        User user = (User) session.getAttribute("user");
        address.setUserId(user.getId());
        addressService.update(address);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Address> detail(@PathVariable Long id) {
        return Result.success(addressService.getById(id));
    }

    @PutMapping("/default/{id}")
    public Result<Void> setDefault(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        addressService.setDefault(id, user.getId());
        return Result.success();
    }
}
