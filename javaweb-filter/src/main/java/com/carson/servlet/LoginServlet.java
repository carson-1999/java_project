package com.carson.servlet;

import com.carson.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端请求的参数,如果是admin,就登陆成功,否则跳转回登陆界面
        String username = req.getParameter("username");

        if(username.equals("admin")){
            //登陆成功(创建session,并存储用户的数据 即存储用户的sessionID)
            req.getSession().setAttribute(Constant.USER_SESSION,req.getSession().getId());
            resp.sendRedirect("/sys/success.jsp");
        }
        else{
            //登陆失败,跳转到错误界面
            resp.sendRedirect("/error.jsp");


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
