package com.carson;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//发送简单的邮件(即:不带有图片及附件的邮件)
public class MailDemo1 {
    public static void main(String[] args) throws Exception {
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
            protected PasswordAuthentication getPasswordAuthentication() {
                //发件人的邮件用户名,授权码
                return new PasswordAuthentication("carson.mail@qq.com","xbcvdjxuieiohgfb");
            }
        });
        //开启Session的debug模式,这样可以看到程序发送Email时的运行状态和输出信息
        session.setDebug(true);

        //2: 通过Session得到transport邮件连接发送对象
        Transport transport = session.getTransport();

        //3: 使用邮箱的用户名及授权码 连接上邮件服务器(需要抛出异常)
        transport.connect("smtp.qq.com","carson.mail@qq.com","xbcvdjxuieiohgfb");

        //4: 创建邮件(注意需要传递上面的session对象)
        MimeMessage message = new MimeMessage(session);
           //指明邮件的发件人
        message.setFrom(new InternetAddress("carson.mail@qq.com"));
           //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("2429070946@qq.com"));
           //邮件的标题
        message.setSubject("Carson的JavaMail测试邮件!");
           //邮件的文本内容(注意设置文本类型)
        message.setContent("<h1 style='color:blue'> 你好阿!</h1>","text/html;charset=UTF-8");

        //5： 发送邮件
        transport.sendMessage(message,message.getAllRecipients());

        // 关闭网络资源连接
        transport.close();
        System.out.println("邮件已发送!");
    }
}
