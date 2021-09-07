package com.carson.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*1:要获取下载文件的路径*/
        String reaLPath = "D:\\360downloads\\wpcache\\卡森.jpg";
        /*2:下载的文件名是*/
        String fileName = reaLPath.substring(reaLPath.lastIndexOf("\\") + 1);
        /*3:通过设置让浏览器支持将要下载的东西(如果包含中文,需要使用URLEncoder.encode()编码,否则乱码)*/
        resp.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        /*4:获取下载文件的输入流*/
        FileInputStream in = new FileInputStream(reaLPath);
        /*5:创建缓冲区*/
        int len = 0;
        byte[] buffer = new byte[1024];
        /*6:获取OutputStream对象*/
        ServletOutputStream out = resp.getOutputStream();
        /*7:将FileOutputStream流写入到buffer缓冲区中,使用OutputStream流将缓冲区中的数据输出到客户端*/
        while((len=in.read(buffer))>0){
            out.write(buffer,0,len);
        }
        /*涉及IO操作,需要关闭资源*/
        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
