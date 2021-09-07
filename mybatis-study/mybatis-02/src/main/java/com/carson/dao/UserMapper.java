package com.carson.dao;

import com.carson.pojo.User;

import java.util.List;

public interface UserMapper {
    //查询全部用户
    public List<User> getUserList();
    //根据Id查询用户
    public User getUserById(int id);
    //增加一个用户
    public int addUser(User user);
    //修改一个用户
    public int updateUser(User user);
    //删除一个用户
    public int delUser(int id);

}
