package com.atguigu.crowd.mvc.config;

import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.crowd.serveice.api.AdminService;
import com.atguigu.crowd.serveice.api.AuthService;
import com.atguigu.crowd.serveice.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {
   @Autowired
   private AdminService adminService;

   @Autowired
   private RoleService roleService;

   @Autowired
   private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据账号名称查询Admin对象
        Admin admin = adminService.getAdminByLoginAcct(username);
        // 获取adminId对象
        Integer adminId = admin.getId();
        // 根据adminId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 根据adminId查询角色信息
        List<String> authNameList = authService.getAssginedAuthNameByAdminId(adminId);
        // 创建集合对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 遍历assignedRoleList存入角色信息
        for (Role role : assignedRoleList){
            // 注意，要加前缀
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        // 遍历authNameList存入权限信息
        for (String authName : authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}
