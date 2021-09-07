package com.carson.dao;

import com.carson.pojo.User;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


public class UserDaoTest {
    static Logger logger = Logger.getLogger(UserDaoTest.class);

    /*测试log4j日志的方法*/
    @Test
    public void log4jTest(){
        logger.info("info:进入了Log4j的测试方法");
        logger.debug("debug:进入了Log4j的测试方法");
        logger.error("error:进入了Log4j的测试方法");
    }

    //根据id查询用户(传递用户参数)
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUserById(1);
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //limit分页测试方法
    @Test
    public void getUserByLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //构建map
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            map.put("startIndex",1);
            map.put("pageSize",4);
            List<User> userList = mapper.getUserByLimit(map);

            for (User user : userList) {
                System.out.println(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    //RowBounds实现分页
    @Test
    public void getUserByRowBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //RowBounds实现
        RowBounds rowBounds = new RowBounds(1, 4);

        //通过java代码层面实现分页
        List<User> userList = sqlSession.selectList("com.carson.dao.UserMapper.getUserByRowBounds", null, rowBounds);

        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }

}
