package com.baozi.hmygbackend.service;

import com.baozi.hmygbackend.entity.GoodsCartDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 11493
* @description 针对表【tb_cart】的数据库操作Service
* @createDate 2023-05-26 23:25:09
*/
public interface CartService extends IService<GoodsCartDto> {
    Integer saveCart(GoodsCartDto goodsCartDto);

    Integer updateGoodsCount(Long userId, Long goodsId, Long goodsCount);

    Integer updateGoodsState(Long userId, Long goodsId, Integer goodsState);
}
