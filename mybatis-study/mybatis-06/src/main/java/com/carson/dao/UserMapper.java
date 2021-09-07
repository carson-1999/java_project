package com.carson.dao;

import com.carson.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //使用注解查询全部用户
    @Select("select * from user")
    public List<User> getUsers();
    //使用注解查询特点id用户(简单数据类型需要@Param)
    @Select("select * from user where id = #{id}")
    public User getUserById(@Param("id") int id);
    //注解增加用户(复杂数据类型不用@Param)
    @Insert("insert into user values(#{id},#{name},#{password})")
    public int addUser(User user);
    //注解更新一个用户信息
    @Update("update user set name=#{name},pwd=#{password} where id=#{id}")
    public int updateUser(User user);
    //注解根据id删除一个用户(简单数据类型需要@Param)
    @Delete("delete from user where id=#{uid}")
    public int delUser(@Param("uid")int id);

}
