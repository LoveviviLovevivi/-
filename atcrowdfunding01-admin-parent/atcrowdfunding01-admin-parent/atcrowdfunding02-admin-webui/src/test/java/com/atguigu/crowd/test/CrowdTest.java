package com.atguigu.crowd.test;

import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.mapper.AdminMapper;

import com.atguigu.atcrowdfunding.mapper.RoleMapper;
import com.atguigu.crowd.serveice.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void pwd(){
        String source = "123123";
        String encoded = passwordEncoder.encode(source);
        System.out.println(encoded);
    }

    @Test
    public void testConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testRole(){
        for (int i = 0; i<235; i++){
            roleMapper.insert(new Role(null, "role"+i));
        }
    }

    @Test
    public void test(){
        for (int i = 0; i<238; i++){
            adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd"+i, "userName"+i, "email"+i, null));
        }
    }
    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int count = adminMapper.insert(admin);
        System.out.println(count);
    }

    @Test
    public void testLogger() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 2.根据不同日志级别打印日志
        logger.debug("Debug level!!!");
        logger.debug("Debug level!!!");
        logger.debug("Debug level!!!");

        logger.info("Info level!!!");
        logger.info("Info level!!!");
        logger.info("Info level!!!");

        logger.warn("Warn level!!!");
        logger.warn("Warn level!!!");
        logger.warn("Warn level!!!");

        logger.error("Error level!!!");
        logger.error("Error level!!!");
        logger.error("Error level!!!");
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }
}
