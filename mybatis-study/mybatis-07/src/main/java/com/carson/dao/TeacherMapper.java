package com.carson.dao;

import com.carson.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    //简单sql用注解,复杂sql用xml配置文件
    //使用简单的注解查询特定id的老师
    @Select("select * from teacher where id=#{tid}")
    public Teacher getTeacherById(@Param("tid")int id);
}
