package com.carson.dao;

import com.carson.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //根据Id查询用户
    public User getUserById(int id);
    //limit实现分页
    public List<User> getUserByLimit(Map<String,Integer> map);
    //RowBounds实现分页
    public List<User> getUserByRowBounds();

}
