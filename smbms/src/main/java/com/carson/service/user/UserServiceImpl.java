package com.carson.service.user;

import com.carson.dao.BaseDao;
import com.carson.dao.user.UserDao;
import com.carson.dao.user.UserDaoImpl;
import com.carson.pojo.Role;
import com.carson.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//业务层用户接口的实现类
//注意:业务层都会调用dao层,所以需要引入dao层
public class UserServiceImpl implements UserService  {
    //业务层都会调用dao层,所以需要引入dao层
    private UserDao userDao;
    //无参构造方法,类加载时自动实例化dao层的对象
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    //用户登陆
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        try{
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作(调用dao层)
            user = userDao.getLoginUser(connection, userCode);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            //关闭资源
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    //根调用dao层来修改用户密码
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        //设置修改密码成功与否的标志
        boolean flag = false;
        try{
            connection = BaseDao.getConnection();
            if(userDao.updatePwd(connection,id,password)>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    //查询记录数
    public int getUserCount(String userName, int userRole) {
        //获取数据库连接
        Connection connection = null;
        int count = 0;
        try{
            connection = BaseDao.getConnection();
            //调用dao层逻辑
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭连接,释放资源
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }

    //获取用户列表
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        //获取数据库连接 及 变量初始化
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName: "+userName);
        System.out.println("queryUserRole: "+userRole);
        System.out.println("currentPageNo: "+currentPageNo);
        System.out.println("pageSize: "+pageSize);
        try{
            connection = BaseDao.getConnection();
            //调用dao层逻辑
            userList = userDao.getUserList(connection,userName,userRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭连接,释放资源
            BaseDao.closeResource(connection,null,null);
        }
        return userList;
    }

    //增加用户

    public boolean add(User user) {
        boolean flag = false;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            //一切的增删改操作 都涉及 数据一致性问题 故要设置事务
            //开启JDBC事务管理(false表示开启)
            connection.setAutoCommit(false);
             //业务层调用dao层逻辑
            int updateRows = userDao.add(connection,user);
            //提交事务
            connection.commit();
             //受影响的行数>0,则代表 数据提交成功
            if(updateRows>0){//用户添加成功
                flag = true;
                System.out.println("user add successful!");
            }
            else{//用户添加失败
                System.out.println("user add failed");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
            //出现异常,则回滚事务
            try{
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {
            //关闭资源
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //验证用户编码是否存在

    public boolean userCodeExist(User user) {
        Connection connection = null;
        boolean flag = false;
        try{
            connection = BaseDao.getConnection();
            //业务层调用Dao层逻辑
            flag = userDao.userCodeExist(connection,user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    //根据ID获取一个用户对象

    public User getUserById(String id) {
        Connection connection = null;
        User user = null;
        try{
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }


    //根据id删除User

    public boolean deleteUserById(Integer delId) {
        Connection connection = null;
        boolean flag = false;
        try{
            connection = BaseDao.getConnection();
            //一切的增删改操作 都涉及 数据一致性问题 故要设置事务
            //开启JDBC事务管理(false表示开启)
            connection.setAutoCommit(false);
            //业务层调用dao层逻辑
            int affectedRows = 0;
            affectedRows = userDao.deleteUserById(connection,delId);
            if(affectedRows>0){
                //提交事务
                connection.commit();
                flag = true;
            }
        }catch (Exception e){
            //事务回滚
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            //关闭连接
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //修改用户信息

    public int modifyUser(User user) {
        Connection connection = null;
        int affectedRows = 0;
        try{
            connection = BaseDao.getConnection();
            //业务层调用dao层逻辑
            affectedRows = userDao.modifyUser(connection,user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return affectedRows;
    }
}
