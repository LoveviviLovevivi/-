package com.atguigu.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalHandler {
    @RequestMapping("/")
    public String showPortalPage(){
        // 省略加载数据
        return "portal";
    }
}
