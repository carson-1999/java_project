package com.carson.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决请求响应的中文乱码问题
        //req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        //Cookie，服务端从客户端请求中获得
        Cookie[] cookies = req.getCookies();//返回数组,存在多个Cookie
        //判断cookie是否存在
        if(cookies!=null){
            //如果存在
            out.print("你上一次访问的时间是:");
            //遍历提取cookie
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //获取cookie的key
                if(cookie.getName().equals("lastLoginTime")){
                    //获取Cookie的value
                    long lastLoginTime = Long.parseLong(cookie.getValue());
                    Date date = new Date(lastLoginTime);
                    out.print(date.toLocaleString());
                }
            }
        }else{
            out.print("这是你第一次访问本站!");
            //服务端给客户端响应一个cookie(通过 +"“ 的形式将其数字转化为字符串的最终形式)
            Cookie cookie = new Cookie("lastLoginTime", System.currentTimeMillis()+"");
            //cookie.setMaxAge(10);
            //若原来已存在cookie信息,再使用addCookie()会覆盖原来的cookie信息
            resp.addCookie(cookie);
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
