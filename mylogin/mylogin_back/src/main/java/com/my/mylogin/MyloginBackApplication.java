package com.my.mylogin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.my.mylogin.mapper")
public class MyloginBackApplication {
    private static final Logger log = LoggerFactory.getLogger(MyloginBackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyloginBackApplication.class, args);
        log.info("run success...");
    }

}
