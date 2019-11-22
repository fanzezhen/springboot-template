package com.github.fanzezhen.template.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.github.fanzezhen.template.dao")
@SpringBootApplication(scanBasePackages = {"com.github.fanzezhen.template"})
public class StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class, args);
    }

}
