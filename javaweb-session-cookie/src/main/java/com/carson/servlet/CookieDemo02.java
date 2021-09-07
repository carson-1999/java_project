package com.carson.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //创建一个cookie,名字需要和被删除的cookie的key一致(重新建立同名立即删除类型的Cookie)
        Cookie cookie = new Cookie("lastLoginTime", null);
        //将cookie有效期设置为0,立马过期
        cookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
