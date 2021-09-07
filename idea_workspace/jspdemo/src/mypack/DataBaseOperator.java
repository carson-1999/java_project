package mypack;

import java.sql.*;
import java.util.Set;
import java.util.HashSet;

import com.mysql.cj.protocol.Resultset;
import mypack.Student;


public class DataBaseOperator {
    String logname,password;
    String success = "false",message = "";
    Connection conn;//链接对象
    Statement sql;//执行sql语句的对象
    ResultSet rs;//sql语句执行后的返回结果集对象

    public DataBaseOperator(){//构造函数
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
        }
    }
    //设置user表中的message信息
    public void setMessage(String message){
        this.message = message;
    }
    public String getLogname(){
        return logname;
    }
    public void setLogname(String logname){
        this.logname = logname;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getSuccess(){
        return success;
    }
    public void setSuccess(String success){
        this.success = success;
    }

    //查询数据库中的user表信息(登陆用户)
    public String getMessage(){
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            sql = conn.createStatement();
            //根据logname提取特定用户的记录
            String condition = "select * from user where logname="+""+logname+";";
            rs = sql.executeQuery(condition);
            int rowcount = 0;//返回结果行数计数的变量
            String ps = null;//存储的用户名密码
            while(rs.next()){
                rowcount++;
                logname = rs.getString("logname");
                ps = rs.getString("password");
            }
            //如果根据Logname能找的到记录，且输入密码相同,则用户存在
            if((rowcount==1)&&(password.equals(ps))){
                message = "ok";
                success = "ok";
            }
            else{
                message = "输入的用户和密码不正确";
                success = "false";
            }
            conn.close();
            return message;
        }catch(SQLException E){
            success = "false";
            message = "false";
            return message;
        }
    }

    //根据学号id删除学生记录
    public void delete(int id){
        try{
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            sql.executeUpdate("delete from student where id= "+id+";");
            sql.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //添加学生记录
    public void insert(int id,String name,String age,String gender,String major){
        try{
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            sql.executeUpdate("insert into student values ("+id+","+name+","+age+","+gender+","+"major"+");");
            sql.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //根据学号id更新学生记录
    public void update(int id,String name,String age,String gender,String major){
        try{
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            sql.executeUpdate("update student set id="+id+",name="+name+",age="+age+",gender="+gender+",major="+major+"where id="+id+";");
            sql.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //根据学生的学号或姓名查询学生记录
    public Set<Student> searchStudents(String id,String name){
        try{
            String s = null;
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            if(id.equals("")){
                id = "";
            }
            if(name.equals("")){
                name = "";
            }
            if(id==""&&name==""){
                //如果学号和姓名都为空,则查询所有的学生记录
                s = "select * from student";
            }
            if(id!=""&&name==""){
                //如果学号不为空,而姓名为空,则按学号查询学生记录
                s = "select * from student where id="+id+";";
            }
            if(id==""&&name!=""){
                //如果学号为空,而姓名不为空,则按姓名查询学生记录
                s = "select * from student where name="+name+";";
            }
            if(id!=""&&name!=""){
                //如果学号和姓名都不为空,则学号和姓名同时匹配查询
                s = "select * from student where id="+id+"and name="+name+";";
            }
            Set<Student> sts = new HashSet<Student>();
            rs = sql.executeQuery(s);
            while(rs.next()){
                Student st = new Student();
                st.setId(rs.getInt(1));
                st.setName(rs.getString(2));
                st.setAge(rs.getString(3));
                st.setGender(rs.getString(4));
                st.setMajor(rs.getString(5));
                sts.add(st);
            }
            if(rs!=null){
                rs.close();
            }
            if(sql!=null){
                sql.close();
            }
            return sts;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //查询数据库的student表,获得所有学生记录
    public Set<Student> searchAllStudents(){
        try{
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            String s = "select * from student";
            Set<Student> sts = new HashSet<Student>();
            rs = sql.executeQuery(s);
            while(rs.next()){
                //创建Student类型的对象,并将这些学生对象添加到集合
                Student st = new Student();
                st.setId(rs.getInt(1));
                st.setName(rs.getString(2));
                st.setAge(rs.getString(3));
                st.setGender(rs.getString(4));
                st.setMajor(rs.getString(5));
                sts.add(st);
            }
            if(rs!=null){
                rs.close();
            }
            if(sql!=null){
                sql.close();
            }
            return sts;
        }catch(SQLException E){
            E.printStackTrace();
        }
        return null;
    }

    //根据学号id查询学生记录
    public Student searchOneStudent(int id){
        try{
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=root");
            java.sql.Statement sql = conn.createStatement();
            String s = "select * from student where id="+id+";";
            Student st = new Student();
            rs = sql.executeQuery(s);
            while(rs.next()){
                st.setId(rs.getInt(1));
                st.setName(rs.getString(2));
                st.setAge(rs.getString(3));
                st.setGender(rs.getString(4));
                st.setMajor(rs.getString(5));
            }
            if(rs!=null){
                rs.close();
            }
            if(sql!=null){
                sql.close();
            }
            return st;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //处理字符串的方法，避免出现中文乱码
    public String codeString(String s){
        String new_str = s;
        try{
            new_str = new String(new_str.getBytes("ISO-8859-1"),"utf8");
            return new_str;
        }catch (Exception e){
            return new_str;
        }
    }
}
