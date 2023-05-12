package com.njit.orderManager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.njit.orderManager.mapper")
//@EnableScheduling   //开启定时任务
public class HotelOrderManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelOrderManagerApplication.class, args);
    }


}
