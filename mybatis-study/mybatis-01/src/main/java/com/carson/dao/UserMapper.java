package com.carson.dao;

import com.carson.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //模糊查询示例
    public List<User> getUserListLike(String value);
    //查询全部用户
    public List<User> getUserList();
    //根据Id查询用户
    public User getUserById(int id);
      //使用万能的Map来传递参数
    public User getUserById2(Map<String,Object> map);
    //增加一个用户
    public int addUser(User user);
      //使用万能的Map来传递参数
    public int addUser2(Map<String,Object> map);

    //修改一个用户
    public int updateUser(User user);
    //删除一个用户
    public int delUser(int id);

}
