package com.carson.test;

import java.sql.*;

public class TestJdbc2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //useUnicode=true&characterEncoding=utf-8解决中文乱码问题
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        String username = "root";
        String password = "root";

        //1:加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2:链接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        //3:编写SQL语句(根据业务,不同的sql语句)
        String sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";

        //4:预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
           //注意下标从1开始,setObject()方法也可以对应各种数据类型
           //给各个占位符,设置数据
        preparedStatement.setInt(1,4);
        preparedStatement.setString(2,"carson说JAVA");
        preparedStatement.setString(3, "88888888");
        preparedStatement.setString(4, "2804201143@qq.com");
        preparedStatement.setDate(5,new Date(new java.util.Date().getTime()));
        //5:执行SQL(返回值i代表的是 受影响的行数)
        int i = preparedStatement.executeUpdate();
          //受影响的行数大于0，则插入数据成功
        if(i>0){
            System.out.println("记录插入成功");
        }
        //6:关闭连接:释放资源(先开后关)
        preparedStatement.close();
        connection.close();
    }
}
