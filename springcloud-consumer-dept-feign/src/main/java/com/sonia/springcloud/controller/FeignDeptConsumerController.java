package com.sonia.springcloud.controller;

import com.sonia.springcloud.pojo.Dept;
import com.sonia.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 我发现可以删了 ConfigBean 文件，里面的 RestTemplate ，@LoadBalanced 好像都无关紧要
 * 虽然 @LoadBalanced是 spring-cloud-commons包下的，但它似乎是给 Ribbon使用负载均衡的注解
 * 这时候的restTemplate也就没有用武之地了
 */
@RestController
public class FeignDeptConsumerController {

    @Autowired
    private DeptClientService deptClientService = null;//因为编译时没有实现类，所以要初始写上null，比较安全

    @RequestMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") int id){
        return this.deptClientService.queryById(id);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add( Dept dept){ //get请求时不需要加RequestBody注解 一般只有post才会用到
        return this.deptClientService.addDept(dept);
    }

    @RequestMapping("/consumer/dept/list")
    public List<Dept> list(Dept dept){
        return this.deptClientService.queryAll();
    }
}
