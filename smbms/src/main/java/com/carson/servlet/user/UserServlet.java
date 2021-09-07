package com.carson.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.carson.pojo.Role;
import com.carson.pojo.User;
import com.carson.service.role.RoleService;
import com.carson.service.role.RoleServiceImpl;
import com.carson.service.user.UserService;
import com.carson.service.user.UserServiceImpl;
import com.carson.util.Constants;
import com.carson.util.PageSupport;
import com.mysql.cj.util.StringUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//将部分代码提取出一个方法,实现servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取调用增删改查的是具体要执行哪个的参数
        String method = req.getParameter("method");
        //根据参数取值的不同区调用不同的方法
        if (method.equals("savepwd") && method != null) {//修改新密码的请求
            this.updatePwd(req, resp);
        } else if (method.equals("pwdmodify") && method != null) {//验证旧密码的请求
            this.pwdValidate(req, resp);
        } else if (method.equals("query") && method != null) {//用户管理模块
            this.query(req, resp);
        } else if (method.equals("add") && method != null) {//增加用户的请求
            this.add(req, resp);
        } else if (method.equals("getrolelist")) {//获取角色列表(在下拉框中显示)的ajax请求
            this.getRoleList(req, resp);
        }else if(method.equals("ucexist")){//添加新用户时 用户输入的编码是否已经存在的ajax请求
            this.userCodeExist(req,resp);
        }else if(method.equals("deluser")){//删除特定用户的ajx请求
            this.delUser(req,resp);
        }else if(method.equals("modify")){//请求进入修改用户信息界面并获取原有用户信息的请求
            this.modifyUserRequest(req,resp,"/jsp/usermodify.jsp");
        }else if(method.equals("modifyexe")){//真正落实实现 用户信息修改的表单请求
            this.modifyUser(req,resp);
        }else if(method.equals("view")){//查看具体的 用户的信息 的请求
            this.modifyUserRequest(req,resp,"/jsp/userview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //修改密码提取出来的方法
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从Session里面拿id
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        //拿到用户输入的新密码
        String newPassword = req.getParameter("newpassword");
        //设置布尔标志
        boolean flag = false;
        if (o != null && newPassword != null && newPassword.length() > 0) {
            //调用service层业务逻辑(修改密码)
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newPassword);
            if (flag) {//如果修改成功
                //利用req携带数据给当前页面显示信息
                req.setAttribute("message", "修改密码成功,请退出,使用新密码登陆");
                //密码修改成功,移除当前登陆用户的session
                req.getSession().removeAttribute(Constants.USER_SESSION);
                System.out.println("密码修改成功,且已经移除用户session");
            } else {//如果密码修改失败
                req.setAttribute("message", "修改密码失败");
            }
        } else {//新密码获取有问题
            req.setAttribute("message", "新密码有问题");
        }
        //请求转发将消息提示转发给当前页面
        req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
    }

    //验证旧密码,session登陆后有用户的密码,与session中密码进行验证对比
    public void pwdValidate(HttpServletRequest req, HttpServletResponse resp) {
        //从session里面拿密码和拿出ajax请求提交的输入的旧密码数据
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldPassword = req.getParameter("oldpassword");//ajax请求携带的输入的旧密码数据
        String userPassword = ((User) o).getUserPassword();//取出session中用户登陆的密码
        //万能的map:结果集(HashMap存储键值对)
        Map<String, String> resultMap = new HashMap<String, String>();
        //情况判断
        if (o == null) {//Session失效了,session过期了
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldPassword)) {//输入的密码为空
            resultMap.put("result", "error");
        } else if (oldPassword.equals(userPassword)) {//密码对比成功
            resultMap.put("result", "true");
        } else {//密码对比失败
            resultMap.put("result", "false");
        }
        //HashMap数据转换为json数据进行返回
        try {
            //设置返回的格式
            resp.setContentType("application/json");
            //获取向前端的输出字符流对象
            PrintWriter writer = resp.getWriter();
            //利用阿里巴巴工具类将数据转换成json格式
            writer.write(JSONArray.toJSONString(resultMap));
            //输出流都是有缓冲区的.flush()作用就是清空缓冲区并完成文件写入操作
            writer.flush();
            //关闭IO流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用户管理的方法
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        //获取前端数据
        String queryName = req.getParameter("queryname");//查询的姓名
        String temp = req.getParameter("queryUserRole");//查询的用户角色
        String pageIndex = req.getParameter("pageIndex");//获取当前页数

        //设置默认查询角色 为0
        int queryUserRole = 0;

        //获取用户列表(导入业务层逻辑)
        UserService userService = new UserServiceImpl();
        List<User> userList = null;

        //第一次走这个请求,一定是第一页,且页面大小是固定的
        int pageSize = 5;//可以把这个 页面大小 写到配置文件中 ,方便后期修改
        int currentPageNo = 1;//设置当前页的序号 为1

        //各种对用户输入参数 的判断
        if (queryName == null) {
            queryName = "";
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);//给查询的角色 赋值0 1 2 3
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);//给当前页 赋值 要跳转的页号
        }

        //查询用户的总数(用户记录的数目)(分页: 上一页,下一页的情况)
        int totalCount = userService.getUserCount(queryName, queryUserRole);
        //总页数的支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //获取分成的 页面的 总页数
        int totalPageCount = pageSupport.getTotalPageCount();

        //控制首页和尾页
        //如果页数<1,就显示第一页的东西,大于总页数,则显示最后一页的东西
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        //获取用户分页的列表展示,以及获取角色列表
        userList = userService.getUserList(queryName, queryUserRole, currentPageNo, pageSize);
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        //设置 将相应数据设置给 转发请求 ,即 将数据交给前端相应的el表达式参数
        req.setAttribute("userList", userList);//用户列表
        req.setAttribute("roleList", roleList);//角色列表
        req.setAttribute("currentPageNo", currentPageNo);//当前页面 页号
        req.setAttribute("totalPageCount", totalPageCount);//总的可查询的 页数
        req.setAttribute("queryUserName", queryName);//查询的姓名
        req.setAttribute("queryUserRole", queryUserRole);//查询的角色
        req.setAttribute("totalCount", totalCount);//总共拥有的记录条数

        //转发请求，返回前端
        try {
            req.getRequestDispatcher("/jsp/userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //增加用户的方法
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取前端参数
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        //实例化 存储用户数据的对象 并设置对象相应属性
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);

        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());

        //servlet调用业务层逻辑(注意实例化业务层对象)
        UserServiceImpl userService = new UserServiceImpl();
        if (userService.add(user)) {//用户增加成功,则重定向到用户管理页面
            resp.sendRedirect("/smbms/jsp/user.do?method=query");

        } else {//用户增加失败,将错误信息 利用 请求转发 到当前页面
            req.getRequestDispatcher("/jsp/useradd.jsp").forward(req, resp);
        }
    }

    //获取角色列表(下拉框中显示)
    public void getRoleList(HttpServletRequest req, HttpServletResponse resp) {
        List<Role> roleList = null;
        //servlet调用service层方法(注意需实例化角色service实现对象)
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //List数据转换为json数据进行返回
        try {
            //设置返回的格式 即Json
            resp.setContentType("application/json");
            //获取向前端的输出字符流对象
            PrintWriter writer = resp.getWriter();
            //利用阿里巴巴工具类将数据转换成json格式
            writer.write(JSONArray.toJSONString(roleList));
            //输出流都是有缓冲区的.flush()作用就是清空缓冲区并完成文件写入操作
            writer.flush();
            //关闭IO流
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证输入的用户编码是否已经在数据库中存在
    public void userCodeExist(HttpServletRequest req, HttpServletResponse resp){
        boolean flag = false;
        //万能的map:结果集(HashMap存储键值对)
        Map<String, String> resultMap = new HashMap<String, String>();
        //获取前端参数
        String userCode= req.getParameter("userCode");
        //实例化 存储用户数据的对象 并设置对象相应属性
        User user = new User();
        user.setUserCode(userCode);
        //servlet调用业务层逻辑(注意新建业务层对象)
        UserService userService = new UserServiceImpl();
        flag = userService.userCodeExist(user);
        if(flag){//用户编码已存在
            resultMap.put("userCode", "exist");
            System.out.println("添加用户的用户编码已存在");
        }
        else{//用户编码不存在
            resultMap.put("userCode", "no_exist");
        }
        //HashMap数据转换为json数据进行返回
        try {
            //设置返回的格式
            resp.setContentType("application/json");
            //获取向前端的输出字符流对象
            PrintWriter writer = resp.getWriter();
            //利用阿里巴巴工具类将数据转换成json格式
            writer.write(JSONArray.toJSONString(resultMap));
            //输出流都是有缓冲区的.flush()作用就是清空缓冲区并完成文件写入操作
            writer.flush();
            //关闭IO流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除用户的请求
    public void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取前端 传递过来 删除的id参数
        String uid = req.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(uid);//将字符串的id转换成int基本数据类型
        }catch(Exception e){
            delId = 0;
        }
        //万能的map:结果集(HashMap存储键值对)
        Map<String, String> resultMap = new HashMap<String, String>();
        //对uid各种可能情况的处理
        if(delId<=0){//删除的id不是>0的正常情况
            resultMap.put("delResult","notexist");
        }else{//正常情况
            //servlet调用业务层逻辑
            UserService userService = new UserServiceImpl();
            if(userService.deleteUserById(delId)){
                resultMap.put("delResult","true");
                System.out.println("删除用户成功!");
            }
            else{
                resultMap.put("delResult","false");
                System.out.println("删除用户失败!");
            }
        }
        //把resultMap的hashMap数据类型 转换成json 输出到前端
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    //修改用户信息
    public void modifyUserRequest(HttpServletRequest req, HttpServletResponse resp,String url) throws ServletException, IOException {
        //获取前端传递过来 用户对应的 uid
        String id = req.getParameter("uid");
        if(!StringUtils.isNullOrEmpty(id)){
            //servlet调用业务层方法得到User对象
            UserService userService = new UserServiceImpl();
            User user = userService.getUserById(id);
            //request携带数据进行 请求转发
            req.setAttribute("user",user);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }

    //真正落实实现 用户信息修改的表单请求
    public void modifyUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int affectedRows = 0;
        //获取前端用户参数
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        String uid = req.getParameter("uid");

        //设置用户数据(注意用户对象是从修改用户请求 取过来的user)
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));

        //servlet调用业务层逻辑
        UserService userService = new UserServiceImpl();
        affectedRows = userService.modifyUser(user);
        if(affectedRows>0){
            //信息修改成功则重定向到用户管理界面
            resp.sendRedirect("/smbms/jsp/user.do?method=query");
        }else{
            //信息修改失败 则:请求转发到当前页面
            req.setAttribute("result","信息修改失败");
            req.getRequestDispatcher("/jsp/usermodify.jsp").forward(req,resp);

        }
    }


}
