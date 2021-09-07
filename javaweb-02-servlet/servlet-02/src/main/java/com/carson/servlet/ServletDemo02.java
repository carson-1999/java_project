package com.carson.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        System.out.println("进入了SetvletDemo02");
        //        转发的请求路径
        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/gp");
        //     调用forward(req,resp)实现转发请求
        requestDispatcher.forward(req,resp);
        System.out.println("已成功转发");

    }
}
