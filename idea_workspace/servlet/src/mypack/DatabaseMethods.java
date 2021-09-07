package mypack;

import java.sql.*;
import java.util.*;


public class DatabaseMethods {
    Connection conn = null;
    public DatabaseMethods(){
        init();
    }
    public void init(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/servlet";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url,user,password);
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void insert(Student st){
        try{
            int id = st.getId();
            String name = st.getName();
            int age = st.getAge();
            String gender = st.getGender();
            String major = st.getMajor();

            String sql = "insert into student "+"values("+id+","+name+","+age+","+gender+","+major+");";
            Statement stat = null;
            stat = conn.createStatement();
            stat.executeUpdate(sql);
            if(stat!=null){
                stat.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(String id){
        try{
            Statement stat = null;
            stat = conn.createStatement();
            String sql = "delete from student where id = " + id;
            stat.executeUpdate(sql);
            if(stat != null){
                stat.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void update(Student st){
        try{
            int id = st.getId();
            String name = st.getName();
            int age = st.getAge();
            String gender = st.getGender();
            String major = st.getMajor();

            String sql = "update student set id = "+id+",name="+name+",age="+age+",gender="+gender+",major="+major+"where id = "+id+";";
            Statement stat = null;
            stat = conn.createStatement();
            stat.executeUpdate(sql);
            if(stat!=null){
                stat.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Set<Student> selectStudents(String id,String name){
        try{
            Statement stat = null;
            ResultSet rs = null;
            stat = conn.createStatement();
            Set<Student> sts = new HashSet<Student>();
            if(id==null){
                id="";
            }
            if(name==null){
                name = "";
            }
            if(id == "" && name == ""){
                rs = stat.executeQuery("select * from student");
            }
            if(id != "" && name == ""){
                rs = stat.executeQuery("select * from student where id="+id+";");
            }
            if(id == "" && name != ""){
                rs = stat.executeQuery("select * from student where name="+name+";");
            }
            if(id != "" && name != ""){
                rs  = stat.executeQuery("select * from student where name="+name+"and id="+id+";");
            }
            while(rs.next()){
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setName(rs.getString("name"));
                st.setAge(rs.getInt("age"));
                st.setGender(rs.getString("gender"));
                st.setMajor(rs.getString("major"));
                sts.add(st);
            }
            if(rs != null){
                rs.close();
            }
            if(stat != null){
                stat.close();
            }
            return sts;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
