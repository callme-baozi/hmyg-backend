package com.baozi.hmygbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baozi.hmygbackend.entity.GoodsCartDto;
import com.baozi.hmygbackend.service.CartService;
import com.baozi.hmygbackend.mapper.CartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 11493
 * @description 针对表【tb_cart】的数据库操作Service实现
 * @createDate 2023-05-26 23:25:09
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, GoodsCartDto>
        implements CartService {
    @Resource
    CartMapper cartMapper;

    @Override
    public Integer saveCart(GoodsCartDto goods) {
        Long state = 0l;
        if (goods.getGoodsState() == true) {
            state = 1l;
        }
        Integer count = cartMapper.saveCart(goods.getUserId(),
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getGoodsPrice(),
                goods.getGoodsCount(),
                goods.getGoodsSmallLogo(),
                state);
        return count;
    }

    @Override
    public Integer updateGoodsCount(Long userId, Long goodsId, Long goodsCount) {
        Integer count = cartMapper.updateGoodsCount(userId, goodsId, goodsCount);
        return count;
    }

    @Override
    public Integer updateGoodsState(Long userId, Long goodsId, Integer goodsState) {
        Integer count = cartMapper.updateGoodsState(userId, goodsId, goodsState);
        return count;
    }
}




