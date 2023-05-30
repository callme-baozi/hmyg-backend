package com.baozi.hmygbackend;

import com.baozi.hmygbackend.entity.Swiperdata;
import com.baozi.hmygbackend.mapper.SwiperdataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class HmygBackendApplicationTests {

    @Resource
    private SwiperdataMapper swiperdataMapper;

    @Test
    void test() {
        List<Swiperdata> swiperdata = swiperdataMapper.selectList(null);
        swiperdata.forEach(System.out::println);
    }

}
