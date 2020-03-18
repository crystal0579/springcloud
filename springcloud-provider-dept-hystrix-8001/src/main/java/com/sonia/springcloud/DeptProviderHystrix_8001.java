package com.sonia.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


/**
 * 启动类
 */
@SpringBootApplication
@EnableEurekaClient  //对于 eureka server 而言，提供服务者也是一个client，EnableEurekaClient表示服务启动后自动注册到Eureka中
@EnableDiscoveryClient//便于客户（他自己）发现在Eureka server上的服务列表,即用于controller里面写的discover（）方法
//添加对熔断的支持 添加@EnableCircuitBreaker或者@EnableHystrix
@EnableCircuitBreaker
public class DeptProviderHystrix_8001 {

    public static void main(String[] args){
        SpringApplication.run(DeptProviderHystrix_8001.class,args);
    }

    //增加 Hystrix dashboard的监控 servlet--配合actuator的包一起使用的要，即dependency里要有 actuator的依赖包
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");//即豪猪页面上的https://hystrix-app:port/actuator/hystrix.stream  网址规范
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
