package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_floordata
 * @TableName tb_floordata
 */
@TableName(value ="tb_floordata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floordata implements Serializable {
    /**
     * 
     */
    @JSONField(name="level")
    private Long level;

    /**
     * 
     */
    @JSONField(name="name")
    private String name;

    /**
     * 
     */
    @JSONField(name="image_src")
    private String imageSrc;

    /**
     * 
     */
    @JSONField(name="image_width")
    private String imageWidth;

    /**
     * 
     */
    @JSONField(name="open_type")
    private String openType;

    /**
     * 
     */
    @JSONField(name="navigator_url")
    private String navigatorUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}