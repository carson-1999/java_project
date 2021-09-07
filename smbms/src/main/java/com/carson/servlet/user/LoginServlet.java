package com.carson.servlet.user;

import com.carson.pojo.User;
import com.carson.service.user.UserService;
import com.carson.service.user.UserServiceImpl;
import com.carson.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//servlet属于控制层,调用业务层代码
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet start .....");
        //接收用户的请求,即获取用户输入的用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //调用业务层:即和数据库中的用户数据进行对比
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);//这里已经把登陆的用户给查出来了

        if(user!=null){//查有此人,可以登陆
            //将用户的信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //然后跳转到主页(/代表站点根目录,故需要后面要接项目名)
            resp.sendRedirect("/smbms/jsp/frame.jsp");
        }
        else{//查无此人,无法登陆
            //转发回登陆网页,顺便提示:用户名或者密码错误(req携带信息)
            req.setAttribute("error","用户名或密码错误");
             //转发的/代表有项目名下的web程序根目录,故不用加项目名
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
