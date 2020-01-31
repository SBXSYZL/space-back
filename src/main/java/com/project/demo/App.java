package com.project.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 */
@EnableTransactionManagement
@RestController
@MapperScan("com.project.demo.dao")
@EnableSwagger2
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.project.demo"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

