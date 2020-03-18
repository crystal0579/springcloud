package com.sonia.springcloud;

import com.sonia.rules.DeptProviderRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableDiscoveryClient//这里只需要开启发现服务的enable就可以了，无需像provider一样还要开启EnableEurekaClient
//RibbonClient这个标签只有在需要自己建立Rule的情况下才会用到,name表示哪个serviceId要到这个Rule，也就是说 RibbonClient 可以有多个
@RibbonClient(name = "SPRINGCLOUD-PROVIDER-DEPT", configuration = DeptProviderRule.class)
public class DeptConsumer_80 {

    public static void main(String[] args){
        SpringApplication.run(DeptConsumer_80.class, args);
    }
}
