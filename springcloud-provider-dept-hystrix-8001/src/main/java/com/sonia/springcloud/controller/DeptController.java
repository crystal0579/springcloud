package com.sonia.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sonia.springcloud.pojo.Dept;
import com.sonia.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提供restful服务
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "getHystrixDept")
    public Dept getDept(@PathVariable Integer id){
        Dept dept =  deptService.queryById(id);
        if (dept == null){
            throw new RuntimeException("id=>" + id + ",不存在该用户，或者信息无法找到");
        }
        return dept;
    }

//   此处不需要任何 annotation
    public Dept getHystrixDept(@PathVariable Integer id){
        Dept dept =  new Dept()
                .setDeptno(id)
                .setDeptName("id=>" + id + "没有对应的信息，null--@Hystrix")
                .setDbsource("no relative datasource");
        return dept;
    }
    @GetMapping("/dept/list")
    public List<Dept> getAll(){
        return deptService.queryAll();
    }

    @PostMapping("/dept/add")
    public boolean addDept(@RequestBody Dept dept){
        return deptService.addDept(dept);
    }

    //获得一些配置信息，得到一些具体的服务
    @Autowired
    private DiscoveryClient  discoveryClient;
    /**
     * 获取 Eureka server上的微服务列表清单
     * 服务提供者对于Eureka Server来说也是一个客户
     * 那么对于客户而言它有权利知道你有哪些对外提供的服务存在了
     * @return
     */
    @GetMapping("/dept/discover")
    public Object discover(){
        //获得微服务列表的清单
        List<String> services = discoveryClient.getServices();
        System.out.println("discover the services:==>" + services);

        //其中一个微服务的具体信息,就它自己（本工程）这个微服务吧
        List<ServiceInstance> instances = discoveryClient.getInstances("springcloud-provider-dept");//即对应的 spring.application.name(application.yml)
        for (ServiceInstance instance: instances) {
            System.out.println(
                    instance.getHost() + "\t" +
                            instance.getPort() + "\t" +
                            instance.getUri() + "\t" +
                            instance.getServiceId() + "\t" +
                            instance.getInstanceId() + "\t"
            );
        }
        return this.discoveryClient;
    }
}
