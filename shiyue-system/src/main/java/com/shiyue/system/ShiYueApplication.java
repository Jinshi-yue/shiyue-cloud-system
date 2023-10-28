package com.shiyue.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: shuoxue
 * @CreateTime: 2023-10-19  00:56
 * @Description: TODO
 * @Version: 1.0
 */
@EnableSwagger2
@SpringBootApplication
public class ShiYueApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiYueApplication.class, args);
    }
}
