package com.baozi.hmygbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baozi.hmygbackend.entity.Categories;
import com.baozi.hmygbackend.service.CategoriesService;
import com.baozi.hmygbackend.mapper.CategoriesMapper;
import org.springframework.stereotype.Service;

/**
* @author 11493
* @description 针对表【tb_categories(tb_categories)】的数据库操作Service实现
* @createDate 2023-05-19 15:59:41
*/
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories>
    implements CategoriesService{

}




