package com.baozi.hmygbackend.mapper;

import com.baozi.hmygbackend.entity.GoodsCartDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 11493
 * @description 针对表【tb_cart】的数据库操作Mapper
 * @createDate 2023-05-26 23:25:09
 * @Entity com.baozi.hmygbackend.entity.Cart
 */
public interface CartMapper extends BaseMapper<GoodsCartDto> {

    Integer saveCart(@Param("userId") Long userId,
                  @Param("goodsId") Long goodsId,
                  @Param("goodsName") String goodsName,
                  @Param("goodsPrice") Long goodsPrice,
                  @Param("goodsCount") Long goodsCount,
                  @Param("goodsSmallLogo") String goodsSmallLogo,
                  @Param("state") Long state);

    Integer updateGoodsCount(@Param("userId")Long userId, @Param("goodsId")Long goodsId,  @Param("goodsCount")Long goodsCount);

    Integer updateGoodsState(@Param("userId")Long userId, @Param("goodsId")Long goodsId, @Param("goodsState")Integer goodsState);

}




