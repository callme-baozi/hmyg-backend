package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * tb_attrs
 * @TableName tb_attrs
 */
@TableName(value ="tb_attrs")
@Data
public class Attrs implements Serializable {
    /**
     * 
     */
    @JSONField(name="attr_write")
    private String attrWrite;

    /**
     * 
     */
    @JSONField(name="attr_vals")
    private String attrVals;

    /**
     *
     */
    @JSONField(name="add_price")
    private Long addPrice;

    /**
     * 
     */
    @JSONField(name="attr_name")
    private String attrName;

    /**
     * 
     */
    @JSONField(name="attr_sel")
    private String attrSel;

    /**
     * 
     */
    @JSONField(name="goods_id")
    private Long goodsId;

    /**
     * 
     */
    @JSONField(name="attr_value")
    private String attrValue;

    /**
     * 
     */
    @JSONField(name="attr_id")
    @TableId(type=IdType.AUTO)
    private Long attrId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}