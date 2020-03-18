package com.sonia.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//启动之后,网页端可以直接访问  http://localhost:7001/   zookeeper+dubbo 是通过搭建额外的dubbo-admin jar包访问的
@SpringBootApplication
@EnableEurekaServer //开启 Eureka服务端，表示可以接受别人的注册
public class ConfigEurekaServer_7001 {

    public static void main(String[] args){
        SpringApplication.run(ConfigEurekaServer_7001.class, args);
    }
}
