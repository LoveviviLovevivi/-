package com.atguigu.crowd.serveice.api;

import com.atguigu.atcrowdfunding.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssginedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssginedAuthNameByAdminId(Integer adminId);
}
