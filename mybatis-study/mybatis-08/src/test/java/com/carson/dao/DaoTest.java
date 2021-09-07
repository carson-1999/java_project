package com.carson.dao;

import com.carson.pojo.Student;
import com.carson.pojo.Teacher;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DaoTest {
    public static void main(String[] args) {
        //获取所有老师测试
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> teacherList = mapper.getTeacher2(1);
            for (Teacher teacher : teacherList) {
                System.out.println(teacher);
            }
        }catch (Exception E) {
            E.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
