package com.carson.filter;

import com.carson.pojo.User;
import com.carson.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用户权限过滤(即通过session的有无判断权限的有无)
public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        //获取session需要的是httpservletRequest类型,故需要进行转换类型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //过滤器,从session中获取用户(返回的是object类型,故需要强转)
        User user = (User)request.getSession().getAttribute(Constants.USER_SESSION);
        if(user==null){//没有此session值,代表session已被移除或者用户未登录
            //跳转回错误界面
            response.sendRedirect("/smbms/error.jsp");
        }
        //添加语句,让过滤器继续执行
        filterChain.doFilter(request,response);

    }

    public void destroy() {

    }
}
