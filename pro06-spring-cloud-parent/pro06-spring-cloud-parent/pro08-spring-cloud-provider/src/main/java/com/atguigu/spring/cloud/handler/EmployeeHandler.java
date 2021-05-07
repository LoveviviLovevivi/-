package com.atguigu.spring.cloud.handler;


import com.atguigu.spring.cloud.util.ResultEntity;
import com.atuguigu.spring.cloud.entity.Employee;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeHandler {

	@HystrixCommand(fallbackMethod = "getEmplWithCircuitBreakerBackup")
	@RequestMapping("/provider/get/emp/with/circuit/breaker")
	public ResultEntity<Employee> getEmplWithCircuitBreaker(@RequestParam("signal") String signal) throws InterruptedException {
		if ("quick-bang".equals(signal)){
			throw new RuntimeException();
		}
		if ("slow-bang".equals(signal)){
			Thread.sleep(6000);
		}
		return ResultEntity.successWithData(new Employee(666, "empName666", 66.66));
	}

	public ResultEntity<Employee> getEmplWithCircuitBreakerBackup(@RequestParam("signal") String signal){
		String message = "方法出现问题，执行断路 signal"+signal;
		return ResultEntity.failed(message);
	}
	@RequestMapping("/provider/get/employee/remote")
	public Employee getEmployeeRemote() {
		return new Employee(555, "tom555 ", 555.55);
	}
//	@RequestMapping("/provider/get/employee/remote")
//	public Employee getEmployeeRemote(HttpServletRequest request) {
//		int serverPort = request.getServerPort();
//		return new Employee(555, "tom555 "+serverPort, 555.55);
//	}

}
