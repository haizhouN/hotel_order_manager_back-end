package com.rabbiter.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.rabbiter.hotel.mapper")
@EnableScheduling   //开启定时任务
public class HotelManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagerApplication.class, args);
    }


}
