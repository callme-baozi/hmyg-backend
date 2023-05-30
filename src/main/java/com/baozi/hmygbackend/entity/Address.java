package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 
 * @TableName tb_address
 */
@TableName(value ="tb_address")
@Data
public class Address implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @JSONField(name="add_id")
    private Integer addId;

    /**
     * 
     */
    @JSONField(name="user_id")
    private Long userId;

    /**
     * 
     */
    @JSONField(name="user_name")
    private String userName;

    /**
     * 
     */
    @JSONField(name="tel_number")
    private String telNumber;

    /**
     * 
     */
    @JSONField(name="province_name")
    private String provinceName;

    /**
     * 
     */
    @JSONField(name="city_name")
    private String cityName;

    /**
     * 
     */
    @JSONField(name="county_name")
    private String countyName;

    /**
     * 
     */
    @JSONField(name="detail_info")
    private String detailInfo;

    /**
     * 
     */
    @JSONField(name="is_default")
    private Integer isDefault;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}