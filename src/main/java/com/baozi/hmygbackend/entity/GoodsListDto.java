package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsListDto implements Serializable {
    /**
     *
     */
    @JSONField(name="goods_name")
    private String goodsName;

    /**
     *
     */
    @JSONField(name="hot_mumber")
    private Long hotMumber;

    /**
     *
     */
    @JSONField(name="goods_price")
    private Long goodsPrice;

    /**
     *
     */
    @JSONField(name="goods_small_logo")
    private String goodsSmallLogo;

    /**
     *
     */
    @JSONField(name="goods_id")
    private Long goodsId;

    /**
     *
     */
    @JSONField(name="cat_one_id")
    private Long catOneId;

    /**
     *
     */
    @JSONField(name="goods_big_logo")
    private String goodsBigLogo;

    /**
     *
     */
    @JSONField(name="goods_number")
    private Long goodsNumber;

    /**
     *
     */
    @JSONField(name="is_promote")
    private Integer isPromote;

    /**
     *
     */
    @JSONField(name="cat_two_id")
    private Long catTwoId;

    /**
     *
     */
    @JSONField(name="cat_id")
    private Long catId;

    /**
     *
     */
    @JSONField(name="up_time")
    private Long updTime;

    /**
     *
     */
    @JSONField(name="cat_three_id")
    private Long catThreeId;

    /**
     *
     */
    @JSONField(name="add_time")
    private Long addTime;

    /**
     *
     */
    @JSONField(name="goods_weight")
    private Long goodsWeight;

}
