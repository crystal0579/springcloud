package com.sonia.springcloud.controller;

import com.sonia.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptConsumerController {

    //restTemplate需要在config中注册到spring中，才能使用
    @Autowired//@Autowired按byType自动注入，而@Resource默认按 byName自动注入
    private RestTemplate restTemplate;//提供多种便捷远程服务访问的方法，简单的restful服务方法

    //这是一个hard coding，负载均衡的时候它将是个变量
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    //负载均衡时，后面跟的是服务名 serviceId(即Eureka 网页上的服务列表的application name，也就是provider的spring.application.name)
    private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-DEPT";//最好是大写，因为Eureka网站上是大写的，当然小写也是可以的


    @RequestMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") int id){
        //看到 RestTemplate的作用了吧， 它里面无非就是把获得的json response body 直接转换为 Dept.class的对象
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add( Dept dept){ //get请求时不需要加RequestBody注解 一般只有post才会用到
        //看到 RestTemplate的作用了吧，add方法的请求可能是个get，它里面却转换成了post,并将get的参数自动转换为内部post的请求参数对象dept
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class );
    }

    @RequestMapping("/consumer/dept/list")
    public List<Dept> list(Dept dept){
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }
}
