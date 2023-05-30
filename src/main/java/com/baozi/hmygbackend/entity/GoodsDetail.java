package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * tb_goods_detail
 * @TableName tb_goods_detail
 */
@TableName(value ="tb_goods_detail")
@Data
public class GoodsDetail implements Serializable {
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
    @JSONField(name="goods_cat")
    private String goodsCat;

    /**
     * 
     */
    @JSONField(name="goods_introduce")
    private String goodsIntroduce;

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
    @JSONField(name="is_del")
    private String isDel;

    /**
     * 
     */
    @JSONField(name="goods_state")
    private Long goodsState;

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

    // 补充数据库没有的字段
    @TableField(exist = false)
    private List<Pics> pics;

    @TableField(exist = false)
    private List<Attrs> attrs;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}