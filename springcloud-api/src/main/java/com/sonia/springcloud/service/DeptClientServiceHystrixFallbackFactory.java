package com.sonia.springcloud.service;

import com.sonia.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Hystrix降级服务
 */
@Component
public class DeptClientServiceHystrixFallbackFactory implements FallbackFactory<DeptClientService> {

    //FallbackFactory接口的实现方法
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            //错误熔断时的安全反应
            @Override
            public Dept queryById(Integer id) {
                Dept dept =  new Dept()
                        .setDeptno(id)
                        .setDeptName("id=>" + id + "没有对应的信息，客户已经提供了降级服务，服务已经关闭--@Hystrix")
                        .setDbsource("no relative datasource");
                return dept;
            }

            @Override
            public List<Dept> queryAll() {
                return null;//尽量不要返回null
            }

            @Override
            public boolean addDept(Dept dept) {
                return false;
            }
        };
    }
}
