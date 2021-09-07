package com.carson.dao;

import com.carson.pojo.User;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class daoTest {
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            /*int affectedRows = mapper.updateUser(new User(9,"zhujie1","zzzzzz"));*/
            int affectedRows = mapper.delUser(9);
            /*for (User user : users) {
                System.out.println(user);
            }*/
            if(affectedRows>0){
                System.out.println("Delete成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }
}
