package com.carson.servlet;

import com.carson.pojo.User;
import com.carson.utils.SendMail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        //实例化实体类(利用前端传递过来的参数)
        User user = new User(username, password, email);
        //用户注册成功之后,给用户发送一封邮件(实例化工具类对象,调用发送邮件的工具方法)
        //利用开一个线程来专门发送邮件,防止出现耗时和网站注册人数过多的情况
        SendMail mail = new SendMail(user);
        //启动线程(注意调用start方法才实现多线程,调用run方法结果还是单线程的效果)
        //mail.run()
        mail.start();

        //注册用户提示信息(利用请求转发)
        req.setAttribute("message","注册成功,已经发送一封邮件到邮箱中!");
        req.getRequestDispatcher("/info.jsp").forward(req,resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
