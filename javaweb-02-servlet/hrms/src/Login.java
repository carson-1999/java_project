package hrms.src;

import java.util.Scanner;

public class Login {
    private static Scanner sc = new Scanner(System.in);

    public static void register() throws Exception {
        System.out.println("*****欢迎注册人力资源管理系统*****");
        String sql = "insert into t_user(name,password)values(?,?)";
        while (true) {
            System.out.print("请输入用户名: ");
            String inName = sc.next();
            System.out.print("请设置密码: ");
            String inPassword = sc.next();
            boolean b = DBUtils.queryLogin(inName, inPassword);
            if (b) {
                System.out.println("该用户名已存在,请重新输入...");
            } else {
                DBUtils.executeUpdate(sql, inName, inPassword);
                System.out.print("注册成功!欢迎登陆!是否立即登陆?y/n : ");
                String as = sc.next();
                if ("y".equals(as)) {
                    login();
                }
                break;
            }
        }

    }

    public static void login() throws Exception {
        int count = 0;
        System.out.println("*****欢迎登陆人力资源管理系统*****");
        while (true) {
            System.out.print("请输入用户名: ");
            String inName = sc.next();
            System.out.print("请设置密码: ");
            String inPassword = sc.next();
            boolean b = DBUtils.queryLogin(inName, inPassword);
            if (b) {
                HrmsByJdbc.mainInterface();
            } else {
                count++;
                System.out.println("账号与密码不匹配,请重新输入!\n");
            }
            if (count >= 3) {
                System.out.println("您连续三次输入错误,已退出!");
                break;
            }
        }
    }

    public static void updatePassword() throws Exception {
        System.out.print("请登录后修改密码");
        System.out.println("\n");
        int count = 0;
        System.out.println("*****请修改登陆密码*****");
        while (true) {
            System.out.print("请输入用户名: ");
            String inName = sc.next();
            System.out.print("请设置密码: ");
            String inPassword = sc.next();
            boolean b = DBUtils.queryLogin(inName, inPassword);
            if (b) {
                System.out.println("----修改密码----\n");
                System.out.print("请输入新的密码: ");
                String newPassword = sc.next();
                String sql = "update t_user set password=? where name=?";
                DBUtils.executeUpdate(sql, newPassword, inName);
                System.out.println("修改成功!请重新登陆...");
                login();
            } else {
                count++;
                System.out.println("账号与密码不匹配,请重新输入!");
            }
            if (count == 3) {
                System.out.println("您连续三次输入错误,已退出!");
                break;
            }

        }
    }

}