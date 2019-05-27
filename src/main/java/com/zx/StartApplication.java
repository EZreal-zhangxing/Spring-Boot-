package com.zx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 14:39
 * @note
 */
@ComponentScan("com.zx.*")
@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(StartApplication.class);
        application.run(args);
    }
}
