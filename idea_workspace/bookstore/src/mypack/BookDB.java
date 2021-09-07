package mypack;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

//从最靠近数据库的底层类开始编写java类(主语类)
public class BookDB {
    private ArrayList books;// 存储书的数组对象
    //JDBC操作 参数声明
    private String dbUrl = "jdbc:mysql://localhost:3306/BookDB?characterEncoding=GB2312";
    private String dbUser = "root";
    private String dbPwd = "root";

    //构造方法会实例化对象默认执行,执行加载mysql驱动
    public BookDB() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
    }
    //与驱动建立连接对象(Connection),并返回连接对象
    public Connection getConnection() throws Exception {
        return java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPwd);
    }
    //关闭连接对象
    public void closeConnection(Connection con) throws Exception {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //关闭语句对象
    public void closePrepStmt(PreparedStatement prepstmt) {
        try {
            if (prepstmt != null) {
                prepstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //关闭结果集对象
    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //通过JDBC与数据库建立连接,执行查询全部书的sql语句,并返回所有书的数量
    public int getNumberOfBooks() throws Exception {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        books = new ArrayList(); // 实例化数组对象

        try {
            con = getConnection();
            String selectsql = "select * " + "from books";
            prepStmt = con.prepareStatement(selectsql);
            rs = prepStmt.executeQuery();
            // 当rs的下一行为真,即有数据
            while (rs.next()) {
                BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7));
                books.add(bd);// 添加到数组
            }
        } finally {
            closeResultSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
        return books.size();//返回存储书的数组容器的大小,即书的数量
    }

    //通过JDBC与数据库建立连接,执行查询全部书的sql语句,返回的是包含每本书的信息的数组,即返回所有书的信息
    public Collection getBooks() throws Exception {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        books = new ArrayList(); // 实例化数组对象
        try {
            con = getConnection();
            String selectsql = "select * " + "from books";
            prepStmt = con.prepareStatement(selectsql);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7));
                books.add(bd);// 添加到数组
            }
        } finally {
            closeResultSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
        Collections.sort(books);
        return books;
    }

    //通过JDBC与数据库建立连接,执行查询全部书的sql语句,返回的是查询的特定id号的书的信息,即只返回一本书的详细信息
    public BookDetails getBookDetails(String bookId) throws Exception {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            String selectsql = "select * " + "from books where id = ?";
            prepStmt = con.prepareStatement(selectsql);
            // 传递实参给sql语句的形参问号
            prepStmt.setString(1, bookId);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7));
                prepStmt.close();
                return bd;
            } else {
                return null;
            }
        } finally {
            closeResultSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
    }

    //通过JDBC与数据库建立连接,购买所有书,即计算购物车中的所有书的数量并修改数据库中的对应销量数据
    public void buyBooks(ShoppingCart cart) throws Exception {
        Connection con = null;
        Collection items = cart.getItems();//返回哈希表购物车中所有的订单对象集合
        Iterator i = items.iterator();// 生成迭代器对象(访问如ArrayList,HashMap等的集合)
        try {
            con = getConnection();
            con.setAutoCommit(false);//设置事务自动提交为false
            //调用 i.hasNext() 用于检测集合中是否还有元素。
            while (i.hasNext()) {
                //调用 i.next() 会返回迭代器的下一个元素，并且更新迭代器的状态。
                ShoppingCartItem sci = (ShoppingCartItem) i.next();//获取某种书对应的订单对象
                BookDetails bd = (BookDetails) sci.getItem();//由订单对象获取书本对象
                String id = bd.getBookId();//由书本对象获取其id号
                int quantity = sci.getQuantity();//由订单对象确定其购买的数量
                buyBook(id, quantity, con);//修改数据库书本对象的销量信息
            }
            con.commit();
            con.setAutoCommit(true);//设置事务的自动提交(涉及到数据的修改才需要提交事务)
        } catch (Exception e) {
            con.rollback();//出现错误,则事务回滚
            throw e;//主动抛出异常
        } finally {
            closeConnection(con);
        }
    }

    //通过JDBC与数据库建立连接,购买一种书,修改数据库中的对应id号的书的销量数据(修改数据)
    public void buyBook(String bookId, int quantity, Connection con) throws Exception {
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            String selectsql = "select * " + "from books where id = ?";
            prepStmt = con.prepareStatement(selectsql);
            prepStmt.setString(1, bookId);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                prepStmt.close();
                String updatesql = "update books set saleAmount = saleAmount + ? where id = ?";
                prepStmt = con.prepareStatement(updatesql);
                prepStmt.setInt(1, quantity);
                prepStmt.setString(2, bookId);
                prepStmt.executeUpdate();
                prepStmt.close();
            }
        } finally {
            closeResultSet(rs);
            closePrepStmt(prepStmt);
        }
    }

}
