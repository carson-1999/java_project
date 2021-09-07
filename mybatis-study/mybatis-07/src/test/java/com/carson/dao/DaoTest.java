package com.carson.dao;

import com.carson.pojo.Student;
import com.carson.pojo.Teacher;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class DaoTest {
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            Teacher teacher = mapper.getTeacherById(1);
            System.out.println(teacher);
        }catch (Exception E) {
            E.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }

    @Test
    public void getStudents(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> studentList = mapper.getStudents2();
            for (Student student : studentList) {
                System.out.println(student);
            }
        }catch (Exception E) {
            E.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
