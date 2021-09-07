package com.carson.dao.user;

import com.carson.pojo.Role;
import com.carson.pojo.User;
import com.carson.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//dao层的用户接口,得到用户数据
public interface UserDao {
    //得到要登陆的用户
    public User getLoginUser(Connection connection,String userCode) throws SQLException;
    //修改当前用户密码
    public int updatePwd(Connection connection,int id,String userPassword) throws SQLException;
    //查询用户总数
    public int getUserCount(Connection connection,String userName,int userRole) throws SQLException;
    //获取用户列表
    public List<User> getUserList(Connection connection,String userName,int userRole,int currentPageNo,int pageSize)throws SQLException;
    //增加用户
    public int add(Connection  connection,User user) throws SQLException;
    //用户编码是否存在
    public boolean userCodeExist(Connection connection,User user) throws SQLException;
    //根据id获取用户
    public User getUserById(Connection connection,String id) throws SQLException;
    //根据id删除用户
    public int deleteUserById(Connection connection,Integer delId) throws SQLException;
    //修改用户信息
    public int modifyUser(Connection connection,User user) throws SQLException;
}
