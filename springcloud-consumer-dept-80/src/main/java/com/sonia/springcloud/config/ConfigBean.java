package com.sonia.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration//相当于以前 spring中的 applicationContext.xml
public class ConfigBean {

    /**
     * 配置负载均衡 可以实现RestTemplate
     * 在 IRule中 有setLoadBalancer 方法，所以看它的实现类
     * 下面的myRule方法可以设置哪个规则Rule
     */
    @Bean
    @LoadBalanced //Ribbon 配置负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 在 IRule中 有setLoadBalancer 方法，所以看它的实现类
     * RoundRobinRule 整体循环机制 默认
     * RandomRule 随机机制
     * AvailabilityFilteringRule 先会过滤掉故障的，跳闸的服务，再进行轮询
     * RetryRule 先会按照轮询的机制获取服务，如果服务获取失败，就会在规定的时间内进行重试
     * 当然你也可以自己创造一个Rule ,最好不要放在com.sonia.springcloud里面及其子目录，springcloud官方文档也是这么介绍的
     * 因为它是springboot启动类的所在环境，
     * 里面的类一旦@什么标签就会被springboot扫描。那为什么不让springboot去扫呢？
     * 因为我可能会建立各种各样的Rule，而项目启动后只能选择其中一个rule
     * 所以这里的configuration 就不来创建下面这个myRule方法了

    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
     */
}
