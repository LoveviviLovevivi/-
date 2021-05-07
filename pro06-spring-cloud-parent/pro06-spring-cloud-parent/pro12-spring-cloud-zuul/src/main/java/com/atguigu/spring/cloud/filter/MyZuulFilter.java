package com.atguigu.spring.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class MyZuulFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(MyZuulFilter.class);

    // 判断当前请求要不要进行过滤
    // 要过滤：返回true，继续执行run()方法
    // 不过滤：返回false，直接放行
    @Override
    public boolean shouldFilter() {
        // 获取requestContext对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取request对象
        HttpServletRequest request = requestContext.getRequest();
        // 判断当前参数是否为hello
        String parameter = request.getParameter("signal");
        return "hello".equals(parameter);
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("当前请求要求过滤");
        // 当前实现会忽略方法的返回值，所以返回null
        return null;
    }

    @Override
    public String filterType() {
        // 返回过滤器的类型，决定当前过滤器什么时候执行
        // pre表示在当前微服务之前执行
        String filterType = "pre";
        return filterType;
    }

    @Override
    public int filterOrder() {
        return 0;
}
}