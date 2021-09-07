package com.carson.dao.user;

import com.carson.dao.BaseDao;
import com.carson.pojo.Role;
import com.carson.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//dao层用户接口的实现类
public class UserDaoImpl implements UserDao{
    //得到要登陆的用户(从数据库中得到数据后封装到user实体类中)
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if(connection!=null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            //关闭资源
            BaseDao.closeResource(connection, pstm, rs);
        }
        return user;
    }

    //修改当前用户密码
    public int updatePwd(Connection connection,int id,String userPassword) throws SQLException{
        PreparedStatement pstm = null;
        int affectedRows = 0;
        if(connection!=null){
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {userPassword,id};
            affectedRows = BaseDao.execute(connection, pstm, sql, params);
            //关闭资源
            BaseDao.closeResource(connection,pstm,null);
        }
        return affectedRows;
    }
    //查询用户总数
    public int getUserCount(Connection connection,String userName,int userRole) throws SQLException{
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        //默认条件下的sql语句
        if(connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
            //存放参数(利用arrayList数组链表)
            ArrayList<Object> list = new ArrayList<Object>();

            //不同条件下的拼接sql语句
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");//index:0
            }
            if(userRole>0){
                sql.append(" and u.userRole like ?");
                list.add(userRole);//index:1
            }
            //把List链表转换为数组
            Object[] params = list.toArray();

            System.out.println("输出的最后的完整sql:"+sql.toString());
            //BaseDao层访问数据库
            rs=BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            if(rs.next()){
                //返回提取总记录行数
                count = rs.getInt("count");
            }
            //关闭连接,释放资源
            BaseDao.closeResource(connection,pstm,rs);
        }
        return count;
    }

    //获取用户列表

    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs=  null;
        //存储返回的各个user实体的 链表,即:userList
        ArrayList<User> userList = new ArrayList<User>();
        if(connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            //作为存储给预编译的sql传递参数的数组，list
            ArrayList<Object> list = new ArrayList<Object>();
            //不同条件下的拼接sql语句
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");//index:0
            }
            if(userRole>0){
                sql.append(" and u.userRole like ?");
                list.add(userRole);//index:1
            }
            //在数据库中,分页使用limit
            sql.append(" order by creationDate DESC limit ?,?");
            //limit的第一个下标,起始下标,第二个下标是记录个数,即pageSize
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            //将链表list转格式为数组
            Object[] params = list.toArray();
            System.out.println("输出的完整sql是:"+sql.toString());
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                //设置用户角色
                user.setUserRoleName(rs.getString("userRoleName"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                userList.add(user);
            }
            //关闭连接,释放资源
            BaseDao.closeResource(connection,pstm,rs);
        }
        return userList;
    }

    //增加用户
    public int add(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(connection!=null){
            String sql = "insert into smbms_user (userCode,userName,userPassword,userRole,gender,birthday,phone,address,creationDate,createdBy) values (?,?,?,?,?,?,?,?,?,?);";
            //从实体类 中获取实参列表
            Object[] params = {user.getUserCode(),user.getUserName(),user.getUserPassword(),user.getUserRole(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getCreationDate(),user.getCreatedBy()};
            //执行SQL
            updateRows = BaseDao.execute(connection,pstm,sql,params);
            //关闭连接
            //BaseDao.closeResource(connection,pstm,null);
        }
        return updateRows;
    }

    //用户编码是否已经存在

    public boolean userCodeExist(Connection connection, User user) throws SQLException {
        boolean flag = false;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        //获取验证的userCode
        String userCode = user.getUserCode();
        Object[] params = {userCode};
        if(connection!=null){
            //预编译的查询sql语句
            String sql = "select * from smbms_user where userCode=?";
            //传递sql参数,执行sql
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            if(rs.next()){//用户code已存在
                flag=true;
            }
            //关闭连接
            BaseDao.closeResource(connection,pstm,rs);
        }
        return flag;
    }

    //根据id获取用户

    public User getUserById(Connection connection, String id) throws SQLException {
        Object[] params = {id};
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if(connection!=null){
            //预编译的sql语句
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole=r.id;";
            user= new User();
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                //注意设置角色Name
                user.setUserRoleName(rs.getString("userRoleName"));
            }
        }
        return user;
    }


    //根据Id删除用户

    public int deleteUserById(Connection connection, Integer delId) throws SQLException {
        //sql执行的前置条件
        int affectedRows = 0;
        Object[] params = {delId};
        PreparedStatement pstm = null;
        if(connection!=null){
            //预编译的sql语句
            String sql = "delete from smbms_user where id=?;";
            //执行sql,获取受影响的行数
            affectedRows = BaseDao.execute(connection,pstm,sql,params);
        }
        //返回结果
        return affectedRows;
    }

    //修改用户信息

    public int modifyUser(Connection connection, User user) throws SQLException {
        //获取用户数据
        Integer id = user.getId();
        String userName = user.getUserName();
        Integer gender = user.getGender();
        Date birthday = user.getBirthday();
        String phone = user.getPhone();
        String address = user.getAddress();
        Integer userRole = user.getUserRole();
        PreparedStatement pstm = null;
        //sql执行前置条件
        Object[] params = {userName,gender,birthday,phone,address,userRole,id};
        int affectedRows = 0;
        if(connection!=null){
            String sql = "update smbms_user set userName=?,gender=?,birthday=?,phone=?,address=?,userRole=? where id=?;";
            System.out.println("修改语句:"+sql);
            affectedRows = BaseDao.execute(connection,pstm,sql,params);
        }
        return affectedRows;
    }

}
