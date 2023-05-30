package com.baozi.hmygbackend.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baozi.baoziclientsdk.client.BaoziApiClient;
import com.baozi.hmygbackend.common.R;
import com.baozi.hmygbackend.entity.User;
import com.baozi.hmygbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        String code = (String) params.get("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxa933a7ca72f7ffa3&secret=758b3e848046cae1881ba32e7fdf6d38" +
                "&js_code=" + code + "&grant_type=authorization_code";
        String response = HttpUtil.get(url);
        String session_key = JSON.parseObject(response).getString("session_key");
        String openid = JSON.parseObject(response).getString("openid");
        Map<String, Object> rawData = (Map<String, Object>) params.get("rawData");
        Integer userId_a = (Integer) rawData.get("user_id");
        Long userId = userId_a.longValue(); // integer转long
        // todo 查询用户是否存在
        // 储存user信息
        User user = JSONObject.parseObject(JSON.toJSONString(rawData), User.class);
        user.setUserId(userId);
//        System.out.println(user);
        userService.save(user);
        // 签名认证
        Map<String, String> headerMap = baoziApiClient.getHeaderMap(JSON.toJSONString(rawData)
        );
        Map<String, String> map = new TreeMap<>();
        map.put("token", headerMap.get("sign"));
        map.put("session_key", session_key);
        map.put("openid", openid);
        return R.success(map, "获取成功");
    }

    @PostMapping("/saveUserAvatar")
    public R<Object> saveUserAvatar(@RequestBody String avatarUrl, HttpServletRequest request) {
        String userId = request.getHeader("user_id");
        System.out.println(userId);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAvatarUrl, avatarUrl)
                .eq(User::getUserId, userId);
        userService.update(updateWrapper);
        return R.success("录入成功");
    }

    @PostMapping("/saveNickName")
    public R<Object> saveNickName(@RequestBody String nickName, HttpServletRequest request) {
        String userId = request.getHeader("user_id");
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getNickName, nickName)
                .eq(User::getUserId, userId);
        userService.update(updateWrapper);
        return R.success("录入成功");
    }


}
