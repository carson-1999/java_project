package com.carson.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

public class CookieDemo03 extends HttpServlet {
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
            out.print("你的中文姓名是:");
            //遍历提取cookie
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //获取cookie的key
                if(cookie.getName().equals("name")){
                    System.out.println(cookie.getValue());
                    //URLDecoder解码处理
                    out.print(URLDecoder.decode(cookie.getValue(),"utf-8"));
                }
            }
        }else{
            out.print("这是你第一次访问本站!");
            //遇到中文,采取URLEncoder进行编码处理
            Cookie cookie = new Cookie("name", URLEncoder.encode("张海","utf-8"));
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
