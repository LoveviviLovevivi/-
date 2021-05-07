package com.atguigu.spring.cloud.api;

import com.atguigu.spring.cloud.entity.Employee;
import com.atguigu.spring.cloud.factory.MyFallBackFactory;
import com.atguigu.spring.cloud.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// value是微服务的名称
// fallbackFactory属性指定provider不可用时提供的备用方案的工厂类型
@FeignClient(value = "atguigu-provider", fallbackFactory = MyFallBackFactory.class)
public interface EmployeeRemoteService {
    // 远程调用的接口方法
    // 要去RequestMapping注解映射的地址一致
    // 要去方法声明一致
    @RequestMapping("/provider/get/employee/remote")
    public Employee getEmployeeRemote();

    @RequestMapping("/provider/get/emp/with/circuit/breaker")
    public ResultEntity<Employee> getEmplWithCircuitBreaker(@RequestParam("signal") String signal);
}
