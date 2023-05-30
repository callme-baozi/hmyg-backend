package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * tb_categories
 * @TableName tb_categories
 */
@TableName(value ="tb_categories")
@Data
public class Categories implements Serializable {
    /**
     * 
     */
    @JSONField(name="cat_id")
    private Long catId;

    /**
     * 
     */
    @JSONField(name="cat_name")
    private String catName;

    /**
     * 
     */
    @JSONField(name="cat_pid")
    private Long catPid;

    /**
     * 
     */
    @JSONField(name="cat_level")
    private Long catLevel;

    /**
     * 
     */
    @JSONField(name="cat_deleted")
    private Integer catDeleted;

    /**
     * 
     */
    @JSONField(name="cat_icon")
    private String catIcon;


    @TableField(exist = false)
    @JSONField(name="children")
    private List<Categories> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}