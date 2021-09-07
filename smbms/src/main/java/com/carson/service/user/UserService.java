package com.carson.service.user;

import com.carson.pojo.Role;
import com.carson.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//业务层的用户接口,进行用户登陆的业务逻辑
public interface UserService {
    //用户登陆
    public User login(String userCode,String password);
    //根据用户id修改密码
    public boolean updatePwd(int id,String password);
    //查询记录数
    public int getUserCount(String userName,int userRole);
    //获取用户列表
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);
    //增加用户
    public boolean add(User user);
    //用户编码是否存在
    public boolean userCodeExist(User user);
    //根据ID获取用户
    public User getUserById(String id);
    //根据ID删除user
    public boolean deleteUserById(Integer delId);
    //修改用户信息
    public int modifyUser(User user);
}
