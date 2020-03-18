package com.sonia.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient//这里只需要开启发现服务的enable就可以了，无需像provider一样还要开启EnableEurekaClient
@EnableFeignClients (basePackages = {"com.sonia.springcloud.service"})//打开Feign客户端（写上要Feign扫描的service包）
//@ComponentScan(basePackages = {"com.sonia.springcloud"}) 这里其实是没有必要写这句，但是当你的 springcloud-api 的service interface 不在consumer 即本module 下的启动类同级下，是需要加这个配置的，因为 Feign的接口需要被spring注入进来,因为 controller 里要 autowired
public class FeignDeptConsumer_80 {

    public static void main(String[] args){
        SpringApplication.run(FeignDeptConsumer_80.class, args);
    }
}
