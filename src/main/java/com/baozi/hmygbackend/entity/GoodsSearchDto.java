package com.baozi.hmygbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GoodsSearchDto {

    @JSONField(name = "goods_name")
    private String goodsName;

    @JSONField(name = "goods_id")
    private Long goodsId;
}
