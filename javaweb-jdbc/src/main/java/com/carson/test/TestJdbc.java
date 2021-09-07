package com.carson.test;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;

import java.sql.*;

public class TestJdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //useUnicode=true&characterEncoding=utf-8解决中文乱码问题
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        String username = "root";
        String password = "root";

        //1:加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2:链接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        //3:创建向数据库发送SQL语句的对象,statement(不安全)):CRUD
        Statement statement = connection.createStatement();

        //4:编写SQL语句
        String sql = "select * from users";

        //5:执行查询SQL语句,返回一个ResultSet结果集
        ResultSet rs = statement.executeQuery(sql);
         //next()方法判断是否还有数据
        while(rs.next()){
             //getObject()方法可以返回任何数据类型
            System.out.println("id="+rs.getObject("id"));
            System.out.println("name="+rs.getObject("name"));
            System.out.println("password="+rs.getObject("password"));
            System.out.println("email="+rs.getObject("email"));
            System.out.println("birthday="+rs.getObject("birthday"));
        }

        //6:关闭连接:释放资源(先开后关)
        rs.close();
        statement.close();
        connection.close();




    }
}
