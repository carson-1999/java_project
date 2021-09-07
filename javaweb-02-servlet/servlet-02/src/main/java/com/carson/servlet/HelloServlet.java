package com.carson.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了doGet方法!");
//        获取当前的ServletContext对象
        ServletContext context = this.getServletContext();
//        保存一个数据在ServletContext对象中(键值对的形式)
        String username = "Carson";
        context.setAttribute("username",username);

    }
}
