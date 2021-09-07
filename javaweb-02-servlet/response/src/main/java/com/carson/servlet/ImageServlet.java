package com.carson.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Random;

public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*让浏览器隔几秒刷新一次*/
        resp.setHeader("refresh","3");
        /*在内存中创建一个图片*/
        BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
        /*得到图片的画笔*/
        Graphics2D g = (Graphics2D) image.getGraphics();
        /*设置图片的背景颜色*/
        g.setColor(Color.white);
        g.fillRect(0,0,80,20);
        /*给图片写数据*/
        g.setColor(Color.blue);//重置画笔颜色
        g.setFont(new Font(null,Font.BOLD,20));//设置字体
        g.drawString(makeNum(),5,20);//写入随机生成的数字字符串
        /*告诉浏览器,这个请求用图片的方式打开*/
        resp.setContentType("image/jpeg");
        /*网站存在缓存,不让浏览器缓存,-1表示不缓存*/
        resp.setDateHeader("expires",-1);
        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        /*把图片写给浏览器*/
        ImageIO.write(image, "jpg", resp.getOutputStream());


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /*生成随机数的函数*/
    private String makeNum(){
        Random random = new Random();
        String num = random.nextInt(999999)+" ";//输入的数字的位数代表生成的数字位数
        StringBuffer sb = new StringBuffer();//StringBuffer是可变长和改变的字符串
        //保证输出的是6位数
        for (int i = 0; i < 6-num.length() ; i++) {
            sb.append(0);
        }
        num = sb.toString()+num;
        //返回num(生成的随机数)
        return num;




    }
}
