package hrms.src;

import java.sql.Connection;//建立与数据库的连接对象
import java.sql.DriverManager;//建立与数据库的连接
import java.sql.PreparedStatement;//向数据库发送进行了预编译的sql语句,sql语句参数可用?代替,防止sql注入
import java.sql.Statement;//向数据库发送简单的sql语句
import java.sql.ResultSet;//代表sql语句的执行结果

public class DBUtils {
    // public static final String DRIVER = "com.mysql.jdbc.Driver";替换成下面这句
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/hrmsdb?characterEncoding=utf-8";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    // 初始化数据库连接对象的值为空(且为private)
    private static Connection conn = null;

    public static Connection getConnection() throws Exception {
        // 加载mysql数据库驱动(开发推荐的方式)
        Class.forName(DRIVER);
        // 与数据库建立连接(创建数据库连接的对象)
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    public static void closeConnection() throws Exception {
        if (conn != null && conn.isClosed()) {
            conn.close();
            conn = null;
        }
    }

    public int executeUpdate(String sql) throws Exception {
        conn = getConnection();
        Statement st = conn.createStatement();// 相当于cursor游标对象
        int r = st.executeUpdate(sql);// 返回值是当executeUpdate(sql)是INSERT、UPDATE 或 DELETE 语句时,返回的是受影响的行数(即更新的行数)
        closeConnection();
        return r;
    }

    // 两个同名的方法,称为方法的重载,注意(参数类型/数量需要不同)
    // 用到了object... obj 即java的不定参数
    public static int executeUpdate(String sql, Object... obj) throws Exception {
        conn = getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                // .setObject(index,value)传递实参数据给sql语句的?号,注意传递参数的下标从1开始
                // 第二个参数value由于使用Object... obj不定参数的形式,传进去的参数自动放在一个数组中,变成一个数组形式,下标从0开始
                pst.setObject(i + 1, obj[i]);
            }
        }
        // .executeUpdat()发送sql语句,执行更新操作
        int r = pst.executeUpdate();
        closeConnection();
        return r;
    }

    public static ResultSet executeQuery(String sql, Object... obj) throws Exception {
        conn = getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                // .setObject(index,value)传递实参数据给sql语句的?号,Object代表任何数据,注意传递的下标从1开始
                pst.setObject(i + 1, obj[i]);
            }
        }
        // .executeUpdat()发送sql语句,执行查询操作,返回指向数据的游标,用ResultSet表示
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    public static boolean queryLogin(String name, String password) throws Exception {
        String sql = "select name from t_user where name=? and password=?";
        // 调用上面的执行查询方法
        ResultSet rs = executeQuery(sql, name, password);
        // 初始的时候，游标在第一行之前，调用ResultSet.next() 方法，可以使游标指向具体的数据行
        // 如果有数据,rs.next()移动到下一行,就是true
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

}