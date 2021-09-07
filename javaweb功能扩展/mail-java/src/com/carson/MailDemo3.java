package com.carson;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

//发送复杂的邮件 (即:带有图片和附件的邮件)
public class MailDemo3 {
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
           //准备图片数据
        MimeBodyPart image = new MimeBodyPart();
           //图片需经过数据处理  DataHandler即数据处理器
        DataHandler dh = new DataHandler(new FileDataSource("D:\\360downloads\\wpcache\\carson.jpg"));
        image.setDataHandler(dh);//在我们的body中放入这个处理的图片数据
        image.setContentID("1.jpg");//给图片设置一个ID,在下面可以使用
           //准备正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一封带图片<img src='cid:1.jpg'>以及附件的邮件","text/html;charset=UTF-8");
           //附件
        MimeBodyPart attachment = new MimeBodyPart();
           //附件页需经过数据处理
        attachment.setDataHandler(new DataHandler(new FileDataSource("D:\\360downloads\\1.txt")));
        attachment.setFileName("1.txt");//附件设置名字

           //拼装邮件正文内容
        MimeMultipart multipart1 = new MimeMultipart();
        multipart1.addBodyPart(text);
        multipart1.addBodyPart(image);
        multipart1.setSubType("related");//默认设置为mixed表示邮件带图片带正文等情况(并且向下兼容多种情况),这里related表示邮件带图片
           //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(multipart1);

           //拼接附件
        MimeMultipart allFile = new MimeMultipart();
        allFile.addBodyPart(attachment);
        allFile.addBodyPart(contentText);
        allFile.setSubType("mixed");//正文和附件都存在邮件中,所有类型设置为mixed
           //设置到消息中,保存修改
        message.setContent(allFile);//把最后编辑好的邮件放到消息当中
        message.saveChanges();//保存修改

        //5： 发送邮件
        transport.sendMessage(message,message.getAllRecipients());

        // 关闭网络资源连接
        transport.close();
        System.out.println("邮件已发送!");

    }
}
