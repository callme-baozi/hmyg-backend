package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * tb_pics
 * @TableName tb_pics
 */
@TableName(value ="tb_pics")
@Data
public class Pics implements Serializable {
    /**
     * 
     */
    @JSONField(name="pics_mid")
    private String picsMid;

    /**
     * 
     */
    @JSONField(name="pics_sma")
    private String picsSma;

    /**
     * 
     */
    @JSONField(name="pics_big")
    private String picsBig;

    /**
     * 
     */
    @JSONField(name="goods_id")
    private Long goodsId;

    /**
     * 
     */
    @JSONField(name="pics_big_url")
    private String picsBigUrl;

    /**
     * 
     */
    @JSONField(name="pics_sma_url")
    private String picsSmaUrl;

    /**
     * 
     */
    @JSONField(name="pics_id")
    private Long picsId;

    /**
     * 
     */
    @JSONField(name="pics_mid_url")
    private String picsMidUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}