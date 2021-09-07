package hrms.src;

import java.util.Scanner;

public class MainLogin {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("*****欢迎登陆人力资源管理系统*****");
            System.out.println("* 1.注册 2.登陆 3.修改密码 0.退出 *");
            System.out.print("请选择: ");
            int num = sc.nextInt();
            if (num == 0) {
                System.out.print("\n Thanks For Your Use!");
                break;
            } else {
                switch (num) {
                    case 1:
                        // 静态方法相同类之间的调用,直接方法名()调用
                        // 静态方法不同类之间的调用:通过类名+方法名()调用其它类的静态方法
                        Login.register();
                        break;
                    case 2:
                        Login.login();
                        break;
                    case 3:
                        Login.updatePassword();
                        break;
                    default:
                        System.out.println("请重新输入");
                }
            }
        }
        // 注意需要关闭Scanner输入流
        sc.close();
    }
}