import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;


public class Hello {
    private String name;


    public static void main(String[] args) {
        int[][] a = new int[11][11];
        a[1][2] = 2;
        a[2][3] = 3;
        for (int i = 0; i <a.length ; i++) {
            for (int j = 0; j <a[i].length ; j++) {
                System.out.print(a[i][j]+"\t");


            }
            System.out.println();
        }
        //IO流会占用资源,注意关闭
        //数字.for  快速生成for循环,或者fori 快速生成for循环
        //psvm快速生成主函数
        //sout快速生成输出语句
        //alt+insert(ins) 然后选择Constructor快速生成构造方法
        //alt+insert(ins) 然后选择Getter and setter快速生成私有属性的get/set方法对
        //alt+insert(ins) 然后选择Override快速对父类进行方法重写
        //Ctrl+H 可以显示类间的继承层次关系
        //选中特定的语句,Ctrl+Alt+T,快速选择包围语句的常用代码块如try/catch
        //java可以导入特定类的特定方法如Math类的random()方法,不过import后要加static,这称为静态导入包
         //即import static java.lang.Math.random;(达到简化写法的目的)


    }
}
