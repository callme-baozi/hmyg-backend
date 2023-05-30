package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_swiperdata
 * @TableName tb_swiperdata
 */
@TableName(value ="tb_swiperdata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Swiperdata implements Serializable {
    /**
     * 
     */
    @JSONField(name = "image_src")
    private String imageSrc;

    /**
     * 
     */
    @JSONField(name = "open_type")
    private String openType;

    /**
     * 
     */
    @JSONField(name = "foods_id")
    private Long goodsId;

    /**
     * 
     */
    private String navigatorUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}