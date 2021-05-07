package com.atguigu.spring.cloud.factory;

import com.atguigu.spring.cloud.api.EmployeeRemoteService;
import com.atguigu.spring.cloud.entity.Employee;
import com.atguigu.spring.cloud.util.ResultEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实现consumer段服务降级功能
 * 实现FallbackFactory接口时要传入@FeignClient标记的接口名
 * 在create（）方法中返回@FeignClient标记的接口类型的对象，当provider调用失败后，会执行这个对象对应的方法
 * 这个类必须加Component注解将当前类的对象加入IOC容器，当然当前类必须要被要被扫描到
 */
@Component
public class MyFallBackFactory implements FallbackFactory<EmployeeRemoteService> {
    @Override
    public EmployeeRemoteService create(Throwable throwable) {
        EmployeeRemoteService employeeRemoteService = new EmployeeRemoteService() {
            @Override
            public Employee getEmployeeRemote() {
                return null;
            }

            @Override
            public ResultEntity<Employee> getEmplWithCircuitBreaker(String signal) {
                return ResultEntity.failed(throwable.getMessage());
            }
        };
        return employeeRemoteService;
    }
}
