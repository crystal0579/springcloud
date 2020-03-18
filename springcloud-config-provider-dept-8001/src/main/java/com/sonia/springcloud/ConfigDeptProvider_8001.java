package com.sonia.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 启动类
 */
@SpringBootApplication
@EnableEurekaClient  //对于 eureka server 而言，提供服务者也是一个client，EnableEurekaClient表示服务启动后自动注册到Eureka中
@EnableDiscoveryClient//便于客户（他自己）发现在Eureka server上的服务列表,即用于controller里面写的discover（）方法
public class ConfigDeptProvider_8001 {

    public static void main(String[] args){
        SpringApplication.run(ConfigDeptProvider_8001.class,args);
    }

}
