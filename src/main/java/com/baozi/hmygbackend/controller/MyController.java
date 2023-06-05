package com.baozi.hmygbackend.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baozi.baoziclientsdk.client.BaoziApiClient;
import com.baozi.hmygbackend.common.R;
import com.baozi.hmygbackend.entity.User;
import com.baozi.hmygbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RestController
@RequestMapping("my")
public class MyController {

    @Resource
    private BaoziApiClient baoziApiClient;

    @Resource
    private UserService userService;

    private final String appid = "wx23f9d52d69148fe0";
    private final String secret = "ca3c20069755de478f27092e953a72aa";


    @PostMapping("/createOrder")
    public R<Object> createOrder(@RequestBody Map<String, Object> params) {
        System.out.println(params);
        Map<String, String> map = new TreeMap<>();
        map.put("order_number", "D0000901212183");
        return R.success(map, "接收成功");
    }

    @PostMapping("/wxlogin")
    public R<Object> wxlogin(@RequestBody Map<String, Object> params) {
//        System.out.println(params);
        User user;
        String code = (String) params.get("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret +
                "&js_code=" + code + "&grant_type=authorization_code";
        String response = HttpUtil.get(url);
        String session_key = JSON.parseObject(response).getString("session_key");
        String openid = JSON.parseObject(response).getString("openid");
        Map<String, Object> rawData = (Map<String, Object>) params.get("rawData");
        System.out.println("openid = " + openid);
        // todo 查询用户是否存在
        // 在微信小程序中，每个用户的openid都是唯一的。根据openid查询是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenId, openid);
        user = userService.getOne(queryWrapper);
        if (user == null) { // 用户不存在
            // 储存user信息
            user = JSONObject.parseObject(JSON.toJSONString(rawData), User.class);
            user.setOpenId(openid);
            userService.save(user); // insert结束之后会返回主键（user_id）到user
            System.out.println("user_id = " + user.getUserId());
        }
        // 签名认证
        Map<String, String> headerMap = baoziApiClient.getHeaderMap(JSON.toJSONString(rawData));
        Map<String, Object> map = new TreeMap<>();
        map.put("token", headerMap.get("sign"));
        map.put("session_key", session_key);
        map.put("openid", openid);
        map.put("userinfo", user);
        map.put("user_id", user.getUserId());
        return R.success(map, "获取成功");
    }

    @PostMapping("/saveUserAvatar")
    public R<Object> saveUserAvatar(@RequestBody String avatarUrl, HttpServletRequest request) {
        String userId = request.getHeader("user_id");
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAvatarUrl, avatarUrl)
                .eq(User::getUserId, userId);
        userService.update(updateWrapper);
        return R.success("录入成功");
    }

    @PostMapping("/saveNickName")
    public R<Object> saveNickName(@RequestBody String nickName, HttpServletRequest request) {
        String userId = request.getHeader("user_id");
//        System.out.println(userId);
//        System.out.println(nickName);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getNickName, nickName)
                .eq(User::getUserId, userId);
        userService.update(updateWrapper);
        return R.success("录入成功");
    }


}
