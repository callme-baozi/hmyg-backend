package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tb_cart
 */
@TableName(value ="tb_cart")
@Data
public class GoodsCartDto implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO) // 主键自增
    @JSONField(name="user_id")
    @JsonProperty("user_id")
    private Long userId;

    @JSONField(name="goods_id")
    @JsonProperty("goods_id")
    private Long goodsId;

    @JSONField(name="goods_name")
    @JsonProperty("goods_name")
    private String goodsName;

    @JSONField(name="goods_price")
    @JsonProperty("goods_price")
    private Long goodsPrice;

    @JSONField(name="goods_count")
    @JsonProperty("goods_count")
    private Long goodsCount;

    @JSONField(name="goods_small_logo")
    @JsonProperty("goods_small_logo")
    private String goodsSmallLogo;

    @JSONField(name="goods_state")
    @JsonProperty("goods_state")
    private Boolean goodsState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}