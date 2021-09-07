package com.carson.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    //初始化:当web服务器启动时,就自动初始化了,随时等待过滤对象的出现
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter已经初始化");

    }

    /*
    1:过滤器中的所有代码,在过滤特定请求的时候都会执行
    2:必须写chain.foFilter()方法让过滤器继续前进
    */

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        System.out.println("CharacterEncodingFilter执行前");
        //让我们的请求继续走,如果不写,程序到这里就会被拦截停止
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("CharacterEncodingFilter执行后");

    }
    //销毁:当web服务器关闭的时候,会进行销毁
    public void destroy() {
        System.out.println("CharacterEncodingFilter已经销毁");

    }
}
