package com.sonia.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy// 我们一般都是zuul的代理，而不是 server
public class ZuulApplication_9527 {

    public static void main(String[] args){
        SpringApplication.run(ZuulApplication_9527.class, args);
    }
}
