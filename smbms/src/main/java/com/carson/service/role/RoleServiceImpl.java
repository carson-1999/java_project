package com.carson.service.role;

import com.carson.dao.BaseDao;
import com.carson.dao.role.RoleDao;
import com.carson.dao.role.RoleDaoImpl;
import com.carson.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    //引入Dao层对象，并利用无参构造函数实例化dao层接口对象
    private RoleDao roleDao;
    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;

        try{
            connection = BaseDao.getConnection();
            //引入dao层逻辑
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭连接
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }
}
