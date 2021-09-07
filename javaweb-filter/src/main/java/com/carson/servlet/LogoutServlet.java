package com.carson.servlet;

import com.carson.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //执行注销时,清除相应的session属性值
        Object user_session = req.getSession().getAttribute(Constant.USER_SESSION);
        if(user_session!=null){
            req.getSession().removeAttribute(Constant.USER_SESSION);
            //清除session特定属性值后,再返回到登陆界面
            resp.sendRedirect("/Login.jsp");
        }
        else{
            resp.sendRedirect("/Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
