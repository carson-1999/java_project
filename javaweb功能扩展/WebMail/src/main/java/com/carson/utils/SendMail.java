package com.carson.utils;

import com.carson.pojo.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//网站3秒原则: 用户体验
//多线程实现用户体验(异步处理)
//启动一个线程,负责发送邮件
public class SendMail extends Thread {

    //用于给用户发送邮件的邮箱
    private String from = "carson.mail@qq.com";
    //邮箱的用户名
    private String username = "carson.mail@qq.com";
    //邮箱的授权码
    private String  password = "xbcvdjxuieiohgfb";
    //发送邮件的服务器地址
    private String host = "smtp.qq.com";

    private User user;
    //前端传递过来的用户对象实例化对象
    public SendMail(User user){
        this.user = user;
    }

    //重写线程中的run方法
    //当start()方法调用后,线程就绪,等待jvm调用naive的run方法执行线程
    @Override
    public void run() {
        try{
            //创建一个Properties的持久的属性集.属性列表中每个键及其对应值都是一个字符串(Properties使用键值对的形式生成属性集)
            Properties prop = new Properties();//注:prop的get/put(接收Object对象),getProperty/setProperty方法(只能接收String)要对应使用,否则结果可能异常
            prop.setProperty("mail.host","smtp.qq.com");//设置为QQ的邮件服务器
            prop.setProperty("mail.transport.protocol","smtp");//设置邮件的发送协议
            prop.setProperty("mail.smtp.auth","true");//需要验证用户名及授权码

            //关于QQ邮箱,还要设置SSL加密,加上以下代码即可，其它邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable","true");
            prop.put("mail.smtp.ssl.socketFactory",sf);

            //使用JavaMail发送邮件有5个步骤

            //1: 创建定义整个应用程序所需的环境信息的Session对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    //发件人的邮件用户名,授权码
                    return new PasswordAuthentication(username,password);
                }
            });
            //开启Session的debug模式,这样可以看到程序发送Email时的运行状态和输出信息
            session.setDebug(true);

            //2: 通过Session得到transport邮件连接发送对象
            Transport transport = session.getTransport();

            //3: 使用邮箱的用户名及授权码 连接上邮件服务器(需要抛出异常)
            transport.connect(host,username,password);

            //4: 创建邮件(注意需要传递上面的session对象)
            MimeMessage message = new MimeMessage(session);
               //指明邮件的发件人
            message.setFrom(new InternetAddress(from));
               //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
               //指明邮件主题
            message.setSubject("Carson用户注册邮件");

            String info = "恭喜您注册成功,您的用户名: "+user.getUsername()+",您的密码: "+user.getPassword();
               //邮件的文本内容(注意设置文本类型)
            message.setContent(info,"text/html;charset=UTF-8");
            message.saveChanges();
            //5： 发送邮件
            transport.sendMessage(message,message.getAllRecipients());
            // 关闭网络资源连接
            transport.close();
            System.out.println("邮件已发送!");
        }catch(Exception e){
            throw new RuntimeException();
        }
    }
}
