package com.baozi.hmygbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baozi.hmygbackend.common.R;
import com.baozi.hmygbackend.entity.Address;
import com.baozi.hmygbackend.service.AddressService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {
    @Resource
    private AddressService addressService;


    @GetMapping("/getAddress")
    public R<Object> getAddress(@RequestParam("user_id") Long userId) {
//        System.out.println("userId = " + userId);
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, 1);
        Address one = addressService.getOne(queryWrapper);
//        System.out.println(one);
        return R.success(one, "获取成功"); // 这里不需要用JSON.toJson转换小写下划线命名，因为前端使用的就是驼峰
    }

    @PostMapping("/updataDefaultAdd")
    public R<Object> updataDefaultAdd(@RequestBody Address address, HttpServletRequest request) {
        // 从请求头中获取userId
        String user_id = request.getHeader("user_id");
        if(user_id==""){
            return R.error("用户id为空");
        }

        Long userId = Long.parseLong(user_id);
        // 该用户的全部地址isDefault设为0
        LambdaUpdateWrapper<Address> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Address::getUserId, userId);
        queryWrapper.set(Address::getIsDefault, 0);
        addressService.update(queryWrapper);
        // 添加地址
        address.setUserId(userId);
        address.setIsDefault(1);
        addressService.save(address);
        return R.success("请求成功");
    }
}
