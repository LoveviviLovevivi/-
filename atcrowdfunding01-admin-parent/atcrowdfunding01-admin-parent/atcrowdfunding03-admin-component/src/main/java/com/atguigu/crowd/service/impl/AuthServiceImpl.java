package com.atguigu.crowd.service.impl;

import com.atguigu.atcrowdfunding.entity.Auth;
import com.atguigu.atcrowdfunding.entity.AuthExample;
import com.atguigu.atcrowdfunding.mapper.AuthMapper;
import com.atguigu.crowd.serveice.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssginedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectgetAssginedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 删除旧的关联数据
        authMapper.deleteOldRelationship(roleId);
        // 获取authIdlist
        List<Integer> authIdList = map.get("authIdArray");
        // 判断authIdList是否有效
        if (authIdList != null && authIdList.size() > 0){
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssginedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssginedAuthNameByAdminId(adminId);
    }
}
