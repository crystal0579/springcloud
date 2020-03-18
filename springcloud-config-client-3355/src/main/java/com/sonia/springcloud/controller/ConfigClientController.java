package com.sonia.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//spring 对application.yml 和 bootstrap.yml 这两个文件名都是默认加载的  也就谈不上因为 @Value 而要  @EnableConfigurationProperties
public class ConfigClientController {

    @Value("${spring.application.name}")//这里标的并不是本module下的 yml 或 properties 文件的value （也不是server module的文件内容）
    private String applicationName;//而是 通过 config-server 读到的远程 git 仓库里的 某个配置文件的spring.application.name 的value

    @Value("${eureka.client.service-url.defaultZone}")//所以这里要写的是远程目标文件里的key,而不是本module里配置文件的key,但是key的名字 spring里都一样
    private String eurekaServer;

    @Value("${server.port}")
    private String port;

    @GetMapping("/config")
    public String getConfig(){
        return "applicationName" + applicationName + "------"
                + "EurekaServer" + eurekaServer + "------"
                + "port" + port;
    }
}
