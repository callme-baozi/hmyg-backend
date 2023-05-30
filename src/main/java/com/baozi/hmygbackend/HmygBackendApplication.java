package com.baozi.hmygbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.baozi.hmygbackend.mapper")
@ServletComponentScan  // 扫描过滤器等
public class HmygBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmygBackendApplication.class, args);
    }

}
