package com.carson.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class FileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断上传的文件是普通表单还是带文件的表单
        if (!ServletFileUpload.isMultipartContent(req)) {//不是带文件的表单,直接终止
            return;
        }

        //创建上传文件的保存路径,建议在WEB-INF目录下,安全,用户无法访问这里面的文件
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }

        //创建缓存临时文件的文件夹保存路径,同样放在WEB-INF目录下
        //临时路径,假如文件超过了预期的大小,就把它放到临时文件夹中,过几天自动删除 或者题型用户转存为永久
        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File tmpFile = new File(tmpPath);
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }

        //处理上传的文件：(包括1,2,3 三个步骤)
        // 一般都需要通过流来获取,虽然可以使用request.getInputStream()原生态的文件上传流获取,但是十分麻烦
        //建议使用Apache的文件上传组件来实现：common-fileupload,它需要依赖于commons-io组件

        try {
            //1：创建DiskFileItemFactory对象,处理文件上传路径或者大小限制的
            DiskFileItemFactory factory = getDiskFileItemFactory(tmpFile);
            //2：获取ServletFileUpload对象
            ServletFileUpload upload = getServletFileUpload(factory);
            //3: 处理上传的文件
            String msg = uploadParseRequest(upload, req, uploadPath);
            //servlet请求转发请求
            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/info.jsp").forward(req,resp);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过这个工厂设置一个缓冲区,当上传的文件大于这个缓冲区的时候,将它放到临时文件夹中
        factory.setSizeThreshold(1024 * 1024);//缓冲区大小为1M
        factory.setRepository(file);//临时目录的保存目录,需要一个File
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        //2：获取ServletFileUpload对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            //pBytesRead:已经读取到的文件大小
            //pContentLength:文件大小
            //故上传进度可以用: (pBytesRead/pContentLength)*100+"%" 来表示
            public void update(long pBytesRead, long pContentLength, int pItems) {
                //System.out.println("文件总大小是:" + pContentLength + ",目前已上传了:" + pBytesRead);
                System.out.println("上传进度:"+(pBytesRead/pContentLength)*100+"%");
            }
        });

        //处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        //设置单个文件的最大值
        upload.setFileSizeMax(1024 * 1024 * 10);
        //设置总共能够上传文件的大小,1024*1024*10=1kb*1024*10=10M
        upload.setSizeMax(1024 * 1024 * 10);
        return upload;
    }

    public static String uploadParseRequest(ServletFileUpload upload, HttpServletRequest request, String uploadPath) throws FileUploadException, IOException {
        String msg = "";
        //把前端请求解析,封装成一个FileItem对象,需要从ServletFileUpload对象中获取
        List<FileItem> fileItems = upload.parseRequest(request);
        //fileItem表示一个提交的表单里的每一个表单控件
        for (FileItem fileItem : fileItems) {
            //判断表单控件是普通的控件还是带文件的控件
            if (fileItem.isFormField()) {//普通的表单控件
                //getFieldName()获取的是表单控件的name属性
                String name = fileItem.getFieldName();
                String value = fileItem.getString("UTF-8");//处理乱码
                System.out.println(name+":"+value);
            } else {//带有文件的表单控件
                //----------------处理文件-------------------
                String uploadFileName = fileItem.getName();//获取文件名
                //可能存在文件名不合法的情况
                if (uploadFileName.trim().equals("") || uploadFileName == null) {
                    continue;
                }
                //获得上传的文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                //获得文件的后缀名
                String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
                //使用UUID，保证文件名唯一
                String uuidPath = UUID.randomUUID().toString();

                //----------------完整存放地址-------------------
                String realPath = uploadPath + "/" + uuidPath;
                //给每个文件创建一个对应的文件夹
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()) {
                    realPathFile.mkdir();
                }

                //----------------文件传输-------------------
                //获得文件上传的流
                InputStream inputStream = fileItem.getInputStream();
                //创建一个文件输出流(输出到对应的各个文件对应的文件夹里)
                FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);
                //创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];
                //判断是否读取完毕
                int len = 0;
                //如果大于0说明还存在数据
                while ((len = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                //关闭流(先开后关)
                fos.close();
                inputStream.close();
                msg = "文件上传成功!";
                fileItem.delete();//上传成功,清除临时文件
            }
        }
        return msg;
    }
}