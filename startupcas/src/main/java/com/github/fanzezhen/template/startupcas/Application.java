package com.github.fanzezhen.template.startupcas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.github.fanzezhen.template.dao")
@SpringBootApplication(scanBasePackages = {"com.github.fanzezhen.template"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
