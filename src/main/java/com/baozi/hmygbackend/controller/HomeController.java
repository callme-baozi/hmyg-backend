package com.baozi.hmygbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baozi.hmygbackend.common.R;
import com.baozi.hmygbackend.entity.*;
import com.baozi.hmygbackend.service.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("home")
public class HomeController {
    @Resource
    private SwiperdataService swiperdataService;

    @Resource
    private CatitemsService catitemsService;

    @Resource
    private FloordataService floordataService;

    @Resource
    private CategoriesService categoriesService;

    @Resource
    private GoodsDetailService goodsDetailService;

    @Resource
    private PicsService picsService;

    @Resource
    private AttrsService attrsService;


    @GetMapping("/swiperdata")
    public R<Object> getSwiperData() {
        List<Swiperdata> list = swiperdataService.list();
        Object toJSON = JSON.toJSON(list);   // 将属性从驼峰命名转化为小写下划线
//        list.forEach(System.out::println);
        return R.success(toJSON, "获取成功");
    }

    @GetMapping("/catitems")
    public String getCatitems() {
        List<Catitems> list = catitemsService.list();
        Object toJSON = JSON.toJSON(list);
        return JSON.toJSONString(R.success(toJSON, "获取成功"));
    }

    @GetMapping("/floordata")
    public R<Object> getFloorData() {
        @Data
        class FloorDto {
            Floordata floor_title;
            List<Floordata> product_list;
        }
        ArrayList<FloorDto> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {  // 为什么是1-4 因为首页楼层只有三层
            FloorDto dto = new FloorDto();
            LambdaQueryWrapper<Floordata> query = new LambdaQueryWrapper<>();
            query.eq(Floordata::getLevel, i);
            query.isNull(Floordata::getImageWidth);
            Floordata floorTitle = floordataService.getOne(query);
            dto.floor_title = floorTitle;

            LambdaQueryWrapper<Floordata> query2 = new LambdaQueryWrapper<>();
            query2.eq(Floordata::getLevel, i);
            query2.isNotNull(Floordata::getImageWidth);
            List<Floordata> productList = floordataService.list(query2);
            dto.product_list = productList;

            list.add(dto);
        }
        Object toJSON = JSON.toJSON(list);  // 将属性的命名转化为小写下划线
        return R.success(toJSON, "获取成功");
    }

    @GetMapping("/categories")
    public R<Object> getCategories() {
        LambdaQueryWrapper<Categories> query = new LambdaQueryWrapper<>();
        query.eq(Categories::getCatLevel, 0);
        List<Categories> firstList = categoriesService.list(query);
        for (Categories category : firstList) {
            Long catId = category.getCatId();
            LambdaQueryWrapper<Categories> query2 = new LambdaQueryWrapper<>();
            query2.eq(Categories::getCatLevel, 1);
            query2.eq(Categories::getCatPid, catId);
            List<Categories> secondList = categoriesService.list(query2);

            for (Categories cate : secondList) {
                Long catId2 = cate.getCatId();
                LambdaQueryWrapper<Categories> query3 = new LambdaQueryWrapper<>();
                query3.eq(Categories::getCatLevel, 2);
                query3.eq(Categories::getCatPid, catId2);
                List<Categories> thirdList = categoriesService.list(query3);
                cate.setChildren(thirdList);
            }

            category.setChildren(secondList);
        }

        Object toJSON = JSON.toJSON(firstList);
        return R.success(toJSON, "获取成功");
    }

    @GetMapping("/getGoodsList")
    public R<Object> getGoodsList(HttpServletRequest request) { // 可以获取可变参数，指定参数

        int pagenum = Integer.parseInt(request.getParameter("pagenum") == null ? "1" : request.getParameter("pagenum"));
        int pagesize = Integer.parseInt(request.getParameter("pagesize") == null ? "10" : request.getParameter("pagesize"));
        Integer cid = null;
        String query = null;

        LambdaQueryWrapper<GoodsDetail> queryWrapper = new LambdaQueryWrapper<>();

        if (request.getParameter("cid") != null && request.getParameter("cid") != "") {
            cid = Integer.parseInt(request.getParameter("cid"));
            queryWrapper.eq(GoodsDetail::getCatId, cid);
        }
        if (request.getParameter("query") != null && request.getParameter("query") != "") {
            query = request.getParameter("query");
            queryWrapper.like(GoodsDetail::getGoodsName, query);
        }

        Page page = new Page(pagenum, pagesize);
        goodsDetailService.page(page, queryWrapper);
        List<GoodsDetail> list = page.getRecords();

        List<GoodsListDto> listDtos = new ArrayList<>();
        for (GoodsDetail goodsDetail : list) { // 去除多余数据
            GoodsListDto goodsListDto = new GoodsListDto();
            BeanUtils.copyProperties(goodsDetail, goodsListDto);
            listDtos.add(goodsListDto);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", listDtos.size());
        map.put("pagenum", pagenum);
        map.put("goods", listDtos);

        Object toJSON = JSON.toJSON(map);
        return R.success(toJSON, "获取成功");
    }


    // 调用Mapper 的分页查询函数
//    @GetMapping("/getGoodsList")
//    public R<Object> getGoodsList(HttpServletRequest request) {
//
//        System.out.println("query = " + request.getParameter("query"));
//        System.out.println("cid = " + request.getParameter("cid"));
//        System.out.println("pagenum = " + request.getParameter("pagenum"));
//        System.out.println("pagesize = " + request.getParameter("pagesize"));
//
//        int pagenum = Integer.parseInt(request.getParameter("pagenum") == null ? "1" : request.getParameter("pagenum"));
//        int pagesize = Integer.parseInt(request.getParameter("pagesize") == null ? "10" : request.getParameter("pagesize"));
//        Integer cid = null;
//        String query = null;
//
//        if (request.getParameter("cid") != null) {
//            cid = Integer.parseInt(request.getParameter("cid"));
//        }
//        if (request.getParameter("query") != null) {
//            query = request.getParameter("query");
//        }
//
//        IPage<GoodsDetail> page = goodsDetailService.getGoodsList(query, cid, pagenum, pagesize);
//        List<GoodsDetail> list = page.getRecords();
//        Object toJSON = JSON.toJSON(list);
//
//        return R.success(toJSON, "获取成功");
//    }

    @GetMapping("/getGoodsDetail")
    public R<Object> getGoodsDetail(@RequestParam("goods_id") Long goodsId) {
        LambdaQueryWrapper<GoodsDetail> query = new LambdaQueryWrapper<>();
        query.eq(GoodsDetail::getGoodsId, goodsId);
        GoodsDetail one = goodsDetailService.getOne(query);
        // 查询pics
        LambdaQueryWrapper<Pics> query2 = new LambdaQueryWrapper<>();
        query2.eq(Pics::getGoodsId, goodsId);
        List<Pics> picsList = picsService.list(query2);
        one.setPics(picsList);
        // 查询attrs
        LambdaQueryWrapper<Attrs> query3 = new LambdaQueryWrapper<>();
        query3.eq(Attrs::getGoodsId, goodsId);
        List<Attrs> attrsList = attrsService.list(query3);
        one.setAttrs(attrsList);

        Object toJSON = JSON.toJSON(one);

        return R.success(toJSON, "获取成功");
    }

    @GetMapping("/qsearch")
    public R<Object> qsearch(@RequestParam("query") String query) {
        LambdaQueryWrapper<GoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(GoodsDetail::getGoodsId,GoodsDetail::getGoodsName); // 只需要这两个字段
        queryWrapper.like(GoodsDetail::getGoodsName, query);
        List<GoodsDetail> list = goodsDetailService.list(queryWrapper);

        List<GoodsSearchDto> dtoList=new ArrayList<>();
        for (GoodsDetail goodsDetail : list) {
            GoodsSearchDto dto = new GoodsSearchDto();
            BeanUtils.copyProperties(goodsDetail,dto);
            dtoList.add(dto);
        }

        Object toJSON = JSON.toJSON(dtoList);

        return R.success(toJSON, "获取成功");
    }

    @GetMapping("/test")
    public String test() {

        List<Swiperdata> list = swiperdataService.list();
        return JSON.toJSONString(list);
    }
}
