package com.cn.dbcenters;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.cn.dbcenters.dao")
public class DbcentersApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbcentersApplication.class, args);
    }

}
