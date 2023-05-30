package com.baozi.hmygbackend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baozi.hmygbackend.entity.GoodsDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 11493
* @description 针对表【tb_goods_detail(tb_goods_detail)】的数据库操作Service
* @createDate 2023-05-19 16:58:37
*/
public interface GoodsDetailService extends IService<GoodsDetail> {


    public IPage<GoodsDetail> getGoodsList(String query, Integer cid, Integer pageNum, Integer pageSize);
}
