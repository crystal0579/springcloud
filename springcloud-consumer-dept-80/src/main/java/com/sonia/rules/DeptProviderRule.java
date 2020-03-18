package com.sonia.rules;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeptProviderRule {

    @Bean
    public IRule myRule(){
        return new SoniaRandomRule();//默认是轮询，现自定义
    }

}
