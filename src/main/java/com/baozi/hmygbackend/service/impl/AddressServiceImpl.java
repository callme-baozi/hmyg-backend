package com.baozi.hmygbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baozi.hmygbackend.entity.Address;
import com.baozi.hmygbackend.service.AddressService;
import com.baozi.hmygbackend.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 11493
* @description 针对表【tb_address】的数据库操作Service实现
* @createDate 2023-05-29 15:19:45
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




