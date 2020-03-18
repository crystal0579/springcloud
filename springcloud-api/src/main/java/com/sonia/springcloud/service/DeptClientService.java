package com.sonia.springcloud.service;

import com.sonia.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 像极了dubbo提供的接口，这种提供外界标准式的接口
 * 因为是标准接口，按照java 接口该有的标准样子，
 * 里面应该配有这个微服务下可以提供访问的路径
 * @Component 不能漏，因为consumer方需要autowired
 * 话说我还是第一次看到 这种类似 @service的component 注解在 接口上，而不是实现类上
 * 在实现类上注解，这样才符合热插拔式， 如果后期更换实现类，直接注解其他实现类就可以
 * 这应该是响应 @FeignClient 单的实现类
 */
@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT", fallbackFactory = DeptClientServiceHystrixFallbackFactory.class)  //像极了dubbo提供的接口，这种提供外界标准式的接口
@Component //以供消费者的 @Autowired
public interface DeptClientService {

    @GetMapping("/dept/get/{id}")//这里引的是服务端的地址
    public Dept queryById(@PathVariable("id") Integer id);//这里的("id")一定要写，否则运行会报错

    @GetMapping("/dept/list")
    public List<Dept> queryAll();

    @PostMapping("/dept/add")
    public boolean addDept(@RequestBody Dept dept);
}
