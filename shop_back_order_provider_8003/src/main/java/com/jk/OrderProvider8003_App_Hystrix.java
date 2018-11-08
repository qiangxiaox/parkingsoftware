package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
//@EnableCircuitBreaker//对hystrixR熔断机制的支持
@MapperScan(value = "com.jk.order.dao")
public class OrderProvider8003_App_Hystrix {
    public static void main(String[] args)
    {
        SpringApplication.run(OrderProvider8003_App_Hystrix.class, args);
    }

}
