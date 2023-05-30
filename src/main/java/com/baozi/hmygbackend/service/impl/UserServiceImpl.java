package com.baozi.hmygbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baozi.hmygbackend.entity.User;
import com.baozi.hmygbackend.service.UserService;
import com.baozi.hmygbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 11493
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2023-05-29 22:30:04
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




