package com.carson.dao;

import com.carson.pojo.User;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoTest {
    //查询全部用户
    @Test
    public void test(){
        //第一步，获得sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            //方式一:getMapper(获取接口对象，调用方法)
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.getUserList();

            //方式二:(不推荐)
            //List<User> userList = sqlSession.selectList("com.carson.dao.UserDao.getUserList");

            for(User user:userList){
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭sqlSession
            sqlSession.close();
        }
    }

    //根据id查询用户(传递用户参数)
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUserById(2);
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //根据id查询用户(传递map实现)
    @Test
    public void getUserById2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
             //创建map
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mapId",5);
            User user = mapper.getUserById2(map);
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //增加一个用户(传递用户实体对象)
    @Test
    public void addUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int affectedRows = mapper.addUser(new User(5, "test", "111111"));
            if(affectedRows>0){
                //由于一切的增删改都要处理事务,这里要提交事务
                sqlSession.commit();
                System.out.println("插入成功!");
            }
        }catch (Exception e){
            //出现错误,事务回滚
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //增加一个用户(传递Map的key值)
    @Test
    public void addUser2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
             //建立map
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mapId",5);
            map.put("mapName","mapTest");
            map.put("mapPwd","654545");
            int affectedRows = mapper.addUser2(map);
            if(affectedRows>0){
                //增删改需要处理事务,这里提交事务
                sqlSession.commit();
                System.out.println("Map方式增加成功!");
            }
        }catch (Exception e){
            //出现异常,事务回滚
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }


    //修改一个用户
    @Test
    public void updatUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int affectedRows =  mapper.updateUser(new User(5,"test0","000000"));
            if(affectedRows>0){
                //由于一切的增删改都要处理事务,这里要提交事务
                sqlSession.commit();
                System.out.println("修改成功!");
            }
        }catch (Exception e){
            //出现错误,事务回滚
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //删除一个用户
    @Test
    public void delUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int affectedRows = mapper.delUser(5);
            if(affectedRows>0){
                //由于一切的增删改都要处理事务,这里要提交事务
                sqlSession.commit();
                System.out.println("删除成功!");
            }
        }catch (Exception e){
            //出现错误,事务回滚
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    //模糊查询示例
    @Test
    public void getUserListLike(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.getUserListLike("李");

            for (User user : users) {
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
