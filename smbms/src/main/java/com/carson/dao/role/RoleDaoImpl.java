package com.carson.dao.role;

import com.carson.dao.BaseDao;
import com.carson.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        //存储Role的链表
        ArrayList<Role> roleList = new ArrayList<Role>();
        if(connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);

            while (rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                roleList.add(role);
            }
            //关闭连接
            BaseDao.closeResource(connection,pstm,rs);
        }
        return roleList;
    }
}
