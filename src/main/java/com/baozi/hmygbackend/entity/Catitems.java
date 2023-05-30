package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * tb_catitems
 *
 * @TableName tb_catitems
 */
@TableName(value = "tb_catitems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catitems implements Serializable {
    /**
     *
     */
    @JSONField(name = "name")
    private String name;

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
    @JSONField(name = "navigator_url")
    private String navigatorUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}