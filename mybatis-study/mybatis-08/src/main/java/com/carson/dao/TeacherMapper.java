package com.carson.dao;

import com.carson.pojo.Student;
import com.carson.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper {
    //测试获取所有的老师
    //public List<Teacher> getTeacher();

    //获取指定老师下的所有学生及老师的信息
    public List<Teacher> getTeacher(@Param("tid") int id);

    //获取指定老师下的所有学生及老师的信息
    public List<Teacher> getTeacher2(@Param("tid") int id);
}
