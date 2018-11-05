package com.jk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @ClassName : Consumer9001_Feign_App
 * @Author : xiaoqiang
 * @Date : 2018/10/23 9:36:36
 * @Description :
 * @Version ： 1.0
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class Consumer9001_Feign_App {
    public static void main(String[] args) {
        SpringApplication.run(Consumer9001_Feign_App.class, args);
    }
}
