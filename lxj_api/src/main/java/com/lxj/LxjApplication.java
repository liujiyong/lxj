package com.lxj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.lxj.mapper")
public class LxjApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxjApplication.class);
    }
}
