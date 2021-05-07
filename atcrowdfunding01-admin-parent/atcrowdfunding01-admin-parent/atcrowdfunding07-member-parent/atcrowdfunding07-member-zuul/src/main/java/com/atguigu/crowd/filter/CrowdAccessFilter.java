package com.atguigu.crowd.filter;

import com.atguigu.crowd.constant.AccessPassResources;
import com.atguigu.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAccessFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        // 获取RequestContext对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 通过RequestContext获取当前请求对象
        HttpServletRequest request = requestContext.getRequest();
        // 获取servletPath的值
        String servletPath = request.getServletPath();
        // 根据servletPath判断当前请求是否可以直接放行
        boolean containsResult = AccessPassResources.PASS_RES_SET.contains(servletPath);
        if(containsResult){
            return false;
        }
        // 判断当前请求是否为静态资源
        // 返回true，说明是静态资源，取反为false表示不检查
        // 返回false,说明不是静态资源，取反为true，表示要检查
        return !AccessPassResources.judgeCurrentServletPathWetherStaticResource(servletPath);
    }
    @Override
    public String filterType() {
        // 返回“pre”是让在目标微服务前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }



    @Override
    public Object run() throws ZuulException {
        // 获取当前的请求对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 获取当前的session对象
        HttpSession session = request.getSession();

        // 尝试从session对象中获取已经登录的用户
        Object loginMember =  session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        // 判断loginMember是否为空
        if (loginMember == null){

            // 从requestContext中获取response对象
            HttpServletResponse response = requestContext.getResponse();
            // 将提示消息存入session域
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            // 重定向到auth工程的登录页面
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
