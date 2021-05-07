package com.atguigu.crowd.mvc.handler;

import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.crowd.serveice.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    @ResponseBody
    @RequestMapping("/send/array.json")
    public ResultEntity<List<Integer>> testReceiveArrayOne(@RequestBody List<Integer> array, HttpServletRequest request){
        Boolean JudgeResult = CrowdUtil.judgeRequestType(request);
        System.out.println("--------------"+JudgeResult);
        for(Integer number: array ){
            //System.out.println("number="+number);
        }
        ResultEntity<List<Integer>> resultEntity = ResultEntity.successWithData(array);

        return resultEntity;
    }
    @RequestMapping("test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request){
        Boolean JudgeResult = CrowdUtil.judgeRequestType(request);
        System.out.println("--------------"+JudgeResult);
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
//        String a = null;
//        System.out.println(a.length());
        System.out.println(10 / 0   );
        return "target";
    }

}
