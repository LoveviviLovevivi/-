package com.atguigu.crowd.mvc.handler;

import com.atguigu.atcrowdfunding.entity.Menu;
import com.atguigu.crowd.serveice.api.MenuService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    
    @RequestMapping("menu/sava.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        // 查询全部的menu对象
        List<Menu> menuList = menuService.getAll();

        // 声明一个变量用来存储找到的节点
        Menu root = null;
        // 创建 Map 对象用来存储 id 和 Menu 对象的对应关系便于查找父节点
        Map<Integer, Menu> menuMap = new HashMap<>();
        // 遍历menulist填充menumap
        for (Menu menu: menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        // 再次遍历menulist查找根节点，组装父子节点
        for (Menu menu: menuList) {
            Integer pid = menu.getPid();
            if(pid == null){
                root = menu;
                continue;
            }
            // 如果pid不为null,则说明有父节点，找到父节点就可以进行组装，建立父子关系
            Menu father = menuMap.get(pid);
            // 将当前节点存入父节点的集合中
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }
}
