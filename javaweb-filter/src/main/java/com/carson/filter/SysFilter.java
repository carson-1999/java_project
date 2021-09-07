package com.carson.filter;

import com.carson.util.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        //用户登陆的权限控制
        //注意,由于这里的req和resp和servlet的req，resp不一样,需要先进行转化(ServletRequest继承自HttpServletRequest)
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //通过session特定属性值的有无,防止未登陆直接访问受保护的页面
        if(request.getSession().getAttribute(Constant.USER_SESSION)==null){
            //如果没有对应的session属性值,就跳转到错误界面
            response.sendRedirect("/error.jsp");
        }
        //书写这句代码,防止程序卡在过滤器
        filterChain.doFilter(request,response);

    }

    public void destroy() {

    }
}
