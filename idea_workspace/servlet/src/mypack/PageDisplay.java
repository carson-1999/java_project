package mypack;

import java.io.OutputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageDisplay extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public PageDisplay(){
        super();
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        this.doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String op = request.getParameter("op");
        int method = Integer.parseInt(op);
        try{
            if(method==0){
                insert(request,response);//0对应增
            }
            else if(method==1){
                delete(request,response);//1对应删
            }
            else if(method==2){
                update(request,response);//2对应改
            }
            else if(method==3){
                select(request,response);//3对应查
            }
            else{
                add(request,response);//其它对应 add，修改记录
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void insert(HttpServletRequest request,HttpServletResponse response){
        try{
            //getPatameter获取参数对应的都是string类型
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String age = request.getParameter("age");
            String gender = request.getParameter("gender");
            String major = request.getParameter("major");
            //==比较的是两个值的内存地址   equals比较的是各自内存存储的值是否相同
            if(id.equals("")||age.equals("")){
                JOptionPane.showMessageDialog(null,"学号和年龄不能为空,且必须为整数!");
                response.sendRedirect("index.html");
            }
            else if(isNumber(age)==false){
                JOptionPane.showMessageDialog(null,"年龄必须为整数,请重新输入!");
                response.sendRedirect("index.html");
            }
            else{
                Student st = new Student();
                st.setId(Integer.parseInt(id));
                st.setName(name);
                st.setAge(Integer.parseInt(age));
                st.setGender(gender);
                st.setMajor(major);
                DatabaseMethods dbmeth = new DatabaseMethods();
                dbmeth.insert(st);
                response.sendRedirect("ServletTest/PageDisplay?op=3");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void delete(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException,SQLException,ServletException,IOException{
        String id = request.getParameter("id");
        DatabaseMethods dbmeth = new DatabaseMethods();
        dbmeth.delete(id);
        response.sendRedirect("ServletTest/PageDisplay?op=3");
    }

    public void update(HttpServletRequest request,HttpServletResponse response)throws ClassNotFoundException,ServletException,IOException,SQLException{
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String major = request.getParameter("major");
        Student st = new Student();
        st.setId(Integer.parseInt(id));
        st.setName(name);
        st.setAge(Integer.parseInt(age));
        st.setGender(gender);
        st.setMajor(major);
        DatabaseMethods dbmeth = new DatabaseMethods();
        dbmeth.update(st);
        response.sendRedirect("/ServletTest/PageDisplay?op=3");
    }

    public void select(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException,SQLException,IOException{
        List<String>result = new ArrayList<String>();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        if(id==null){
            id="";
        }
        if(name==null){
            name="";
        }
        DatabaseMethods dbmeth = new DatabaseMethods();
        Set<Student> sts = dbmeth.selectStudents(id,name);
        String str = "";
        str = "<table frame=\"border\" bordercolor=\"black\" style=\"width:600px;\">  ";
        result.add(str);
        str="<tr><td style=\"border:1px solid black;\">学号</td>" +
                "<td style=\"border:1px solid black;\">姓名</td>"+
                "<td style=\"border:1px solid black;\">年龄</td>"+
                "<td style=\"border:1px solid black;\">性别</td>"+
                "<td style=\"border:1px solid black;\">专业</td>"+
                "<td style=\"border:1px solid black;\">操作</td>";
        result.add(str);
        Iterator<Student>it = sts.iterator();
        while(it.hasNext()){
            Student st = it.next();
            str = "<tr>";
            str = str+"<td style=\"border:1px solid black;\">"+st.getId()+"</td>";
            str = str+"<td style=\"border:1px solid black;\">"+st.getName()+"</td>";
            str = str+"<td style=\"border:1px solid black;\">"+st.getAge()+"</td>";
            str = str+"<td style=\"border:1px solid black;\">"+st.getGender()+"</td>";
            str = str+"<td style=\"border:1px solid black;\">"+st.getMajor()+"</td>";
            str = str+"<td style=\"border:1px solid black;\">"+"<a href='PageDisplay?op=1&id= "+st.getId()+"'> 删除</a>"+"&nbsp&nbsp&nbsp&nbsp"+"<a href='PageDisplay?op=4&id= "+st.getId()+"'> 修改</a>"+"</td>";
            result.add(str);
        }
        str = "</table>"+"<BR>"+"<a href='index.html'>返回主页面</a>";
        result.add(str);
        //OutPut.outputToClient(result,response);
    }
    //ADD方法
    public void add(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException,SQLException,IOException{
        List<String>result = new ArrayList<String>();
        String id = request.getParameter("id");
        DatabaseMethods dbmeth = new DatabaseMethods();
        Set<Student>sts = dbmeth.selectStudents(id,"");
        Iterator<Student>it = sts.iterator();

        String str = "<form action='/ServletTest/PageDisplay?op=2' method='post' target='workspace'>";
        result.add(str);
        while (it.hasNext()){
            Student st = it.next();
            str = "学号:<input type='text' name='id' value='"+st.getId()+"'><br>"
                    +"姓名:<input type='text' name='name' value='"+st.getName()+"'><br>"
                    +"年龄:<input type='text' name='age' value='"+st.getAge()+"'><br>"
                    +"性别:<input type='text' name='gender' value='"+st.getGender()+"'><br>"
                    +"专业:<input type='text' name='major' value='"+st.getMajor()+"'><br>";
            result.add(str);
        }
        str = "<input type='submit' name='modify' value='修改'><br>"+"<a href=\"/ServletTest/PageDisplay?op=3\" target=\"workspace\">返回查询结果页面</a>"+"</form>";
        result.add(str);
        //OutPut.outputToClient(result,response);
    }

    public boolean isNumber(String str){
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }
        else{
            return true;
        }
    }
}
