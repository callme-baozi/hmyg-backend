package com.baozi.hmygbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baozi.hmygbackend.common.R;
import com.baozi.hmygbackend.entity.GoodsCartDto;
import com.baozi.hmygbackend.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;


    @PostMapping("/addProdToCart")
    public R<Object> addProdToCart(@RequestBody GoodsCartDto goodsCartDto, HttpServletRequest request) {
//        goodsCartDto.setUserId(101l);
//        System.out.println(goodsCartDto);
        if (goodsCartDto.getUserId() == null) {
            log.info("user_id 数据为空");
            return R.error("user_id 数据为空");
        }
        if (goodsCartDto.getGoodsId() == null) {
            log.info("goods_id 数据为空");
            return R.error("goods_id 数据为空");
        }
        // 查询购物车是够有该商品
        String userId = request.getHeader("user_id");
        LambdaQueryWrapper<GoodsCartDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCartDto::getGoodsId, goodsCartDto.getGoodsId())
                .eq(GoodsCartDto::getUserId, userId);
        GoodsCartDto cartDto = cartService.getOne(queryWrapper);
        if (cartDto == null) { // 没有记录。添加商品
            Integer count = cartService.saveCart(goodsCartDto);
            if (count < 1) {
                log.info("添加到购物车失败");
                return R.error("添加到购物车失败");
            }
            return R.success("添加成功", "获取成功");
        }
        // 有记录。数量加1
        LambdaUpdateWrapper<GoodsCartDto> updateWrapper = new LambdaUpdateWrapper<>();
        System.out.println(cartDto.getGoodsCount());
        updateWrapper.set(GoodsCartDto::getGoodsCount, cartDto.getGoodsCount() + 1)
                .eq(GoodsCartDto::getGoodsId, goodsCartDto.getGoodsId())
                .eq(GoodsCartDto::getUserId, userId);
        cartService.update(updateWrapper);
        return R.success("添加成功", "获取成功");

    }

    @GetMapping("/getCartList")
    public R<Object> getCartList(@RequestParam("user_id") Long userId) {
//        System.out.println(request.getRequestURL());
        if (userId == null) {
            return R.error("userId为空");
        }
//        System.out.println("userid = "+userId);
        LambdaQueryWrapper<GoodsCartDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCartDto::getUserId, userId);
        List<GoodsCartDto> list = cartService.list(queryWrapper);
//        System.out.println(list);
        Object toJSON = JSON.toJSON(list);
        return R.success(toJSON, "获取成功");
    }

    @PostMapping("/updateGoodsCount")
    public R<Object> updateGoodsCount(@RequestBody Map<String, Object> params) {
//        System.out.println(params);
        Map<String, Object> goods = (Map<String, Object>) params.get("e");
        Integer userId_a = (Integer) params.get("user_id");
        Long userId = userId_a.longValue(); // Integer转Long
        Integer goodsId_a = (Integer) goods.get("goods_id");
        Long goodsId = goodsId_a.longValue();  // Integer转Long
        Integer goodsCount_a = (Integer) goods.get("goods_count");
        Long goodsCount = goodsCount_a.longValue();  // Integer转Long
        // 查询是否有记录
        LambdaQueryWrapper<GoodsCartDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCartDto::getGoodsId, goodsId)
                .eq(GoodsCartDto::getUserId, userId);
        GoodsCartDto one = cartService.getOne(queryWrapper);
        if (one == null) {
            return R.error("购物车无该数据");
        }
        // 有记录 则修改数量
        Integer count = cartService.updateGoodsCount(userId, goodsId, goodsCount);
        if (count < 1) {
            return R.error("购物车商品数量修改失败");
        }
        return R.success("修改成功");
    }

    @PostMapping("/updateGoodsState")
    public R<Object> updateGoodsState(@RequestBody Map<String, Object> params) {
//        System.out.println(params);
        Map<String, Object> goods = (Map<String, Object>) params.get("e");
        Integer userId_a = (Integer) params.get("user_id");
        Long userId = userId_a.longValue(); // Integer转Long
        Integer goodsId_a = (Integer) goods.get("goods_id");
        Long goodsId = goodsId_a.longValue();  // Integer转Long
        Boolean goodsState_a = (Boolean) goods.get("goods_state");
        Integer goodsState = goodsState_a == true ? 1 : 0;// Boolean转Integer
        // 查询是否有记录
        LambdaQueryWrapper<GoodsCartDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCartDto::getGoodsId, goodsId)
                .eq(GoodsCartDto::getUserId, userId);
        GoodsCartDto one = cartService.getOne(queryWrapper);
        if (one == null) {
            return R.error("购物车无该数据");
        }
        // 有记录 则修改数量
        Integer count = cartService.updateGoodsState(userId, goodsId, goodsState);
        if (count < 1) {
            return R.error("购物车商品勾选状态修改失败");
        }
        return R.success("修改成功");
    }

    @GetMapping("/updateAllGoodsState")
    public R<Object> updateAllGoodsState(@RequestParam("newState") String state, @RequestParam("user_id") Long userId) {
//        System.out.println(state);
//        System.out.println(userId);
        Integer newState = state.equals("true") ? 1 : 0; // 不能用== 判断
        LambdaUpdateWrapper<GoodsCartDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(GoodsCartDto::getGoodsState, newState)
                .eq(GoodsCartDto::getUserId, userId);
        cartService.update(null, updateWrapper);// todo 这个返回值是boolean 是什么意思？
        return R.success("修改成功");
    }

    @GetMapping("/deleteCartPro")
    public R<Object> deleteCartPro(@RequestParam("user_id") Long userId, @RequestParam("goods_id") Long goodsId) {
//        System.out.println(userId);
//        System.out.println(goodsId);
        LambdaQueryWrapper<GoodsCartDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCartDto::getUserId, userId)
                .eq(GoodsCartDto::getGoodsId, goodsId);
        cartService.remove(queryWrapper);
        return R.success("修改成功");
    }
}
