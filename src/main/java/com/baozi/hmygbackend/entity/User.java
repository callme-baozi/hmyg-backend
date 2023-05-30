package com.baozi.hmygbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_user
 */
@TableName(value ="tb_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private String nickName;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String language;

    /**
     * 
     */
    private String city;

    /**
     * 
     */
    private String province;

    /**
     * 
     */
    private String country;

    /**
     * 
     */
    private String avatarUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}