package com.carson.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*原理:
           resp.setHeader("Location","/response/img");
           resp.setStatus(302);临时重定向
           */
        //实现重定向(注意路径需要加项目名,这里的/表示的是站点的根目录(不包括项目名))
        resp.sendRedirect("/response/img");
    }
}
