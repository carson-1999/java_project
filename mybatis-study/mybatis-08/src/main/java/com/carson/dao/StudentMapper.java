package com.carson.dao;

import com.carson.pojo.Student;

import java.util.List;

public interface StudentMapper {
    //查询所有学生的信息及相应的老师信息(嵌套SQL语句的方式)
    public List<Student> getStudents();
    //查询所有学生的信息及相应的老师信息(按照结果嵌套处理)
    public List<Student> getStudents2();

}
