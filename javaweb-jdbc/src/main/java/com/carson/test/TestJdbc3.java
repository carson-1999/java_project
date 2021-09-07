package com.carson.test;

import org.junit.Test;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc3 {
    //由于导入了junit单元测试的依赖,加上注解@Test后即可运行,不需要建对象调用方法进行测试
    @Test
    public void test() throws ClassNotFoundException, SQLException {
        //useUnicode=true&characterEncoding=utf-8解决中文乱码问题
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        String username = "root";
        String password = "root";

        Connection connection = null;

        try{
            //1:加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2:链接数据库,代表数据库
            connection = DriverManager.getConnection(url, username, password);

            //3:通知数据库开启事务,false表示开启(必须要开启)
             //否则会报错:Can't call rollback when autocommit=true
            connection.setAutoCommit(false);

            String sql1 = "update account set money = money-100 where name='A'";
            connection.prepareStatement(sql1).executeUpdate();

            //制作错误
            //int i = 1/0;

            String sql2 = "update account set money = money+100 where name='B'";
            connection.prepareStatement(sql2).executeUpdate();

            //以上两条sql都执行成功了，就提交事务
            connection.commit();
            System.out.println("事务执行成功!");
        }catch(Exception e){
            //如果出现异常,就回滚事务
            connection.rollback();
            System.out.println("Faied，出现异常!");
            e.printStackTrace();
        }finally {
            //关闭连接，释放资源
            connection.close();
        }
    }
}