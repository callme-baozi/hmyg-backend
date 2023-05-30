package com.baozi.hmygbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baozi.hmygbackend.entity.GoodsDetail;
import com.baozi.hmygbackend.service.GoodsDetailService;
import com.baozi.hmygbackend.mapper.GoodsDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 11493
* @description 针对表【tb_goods_detail(tb_goods_detail)】的数据库操作Service实现
* @createDate 2023-05-19 16:58:37
*/
@Service
public class GoodsDetailServiceImpl extends ServiceImpl<GoodsDetailMapper, GoodsDetail>
    implements GoodsDetailService{

    @Resource
    private GoodsDetailMapper goodsDetailMapper;

    @Override
    public IPage<GoodsDetail> getGoodsList(String query,Integer cid,Integer pageNum,Integer pageSize){
        LambdaQueryWrapper<GoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(cid!=null){
            queryWrapper.eq(GoodsDetail::getCatId,cid);
        }
        if(query!=null){
            queryWrapper.like(GoodsDetail::getGoodsName,query);
        }
        Page<GoodsDetail> page = new Page<>(pageNum,pageSize);
        IPage<GoodsDetail> goodsListPage = goodsDetailMapper.selectPage(page, queryWrapper);
        return goodsListPage;
    }

}




