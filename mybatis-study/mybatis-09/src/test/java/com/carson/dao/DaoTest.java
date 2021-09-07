package com.carson.dao;

import com.carson.pojo.Blog;
import com.carson.utils.IDutils;
import com.carson.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.IdentityHashMap;

/*镇压所有警告信息*/
@SuppressWarnings("all")
public class DaoTest {
    //测试插入数据
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try{
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            //create a blog object
            Blog blog = new Blog();
            blog.setId(IDutils.getId());//ID设置为UUID,确保唯一性
            blog.setTitle("Mybatis如此easy!");
            blog.setAuthor("Carson");
            blog.setCreateTime(new Date());
            blog.setViews(999);
            int affectedRows = mapper.addBlog(blog);
            if(affectedRows>0){
                System.out.println("插入数据成功");
            }
            blog.setId(IDutils.getId());
            blog.setTitle("JAVA如此easy!");
            affectedRows = mapper.addBlog(blog);
            if(affectedRows>0){
                System.out.println("插入数据成功");
            }
            blog.setId(IDutils.getId());
            blog.setTitle("Spring如此easy!");
            affectedRows = mapper.addBlog(blog);
            if(affectedRows>0){
                System.out.println("插入数据成功");
            }
            blog.setId(IDutils.getId());
            blog.setTitle("微服务如此easy!");
            affectedRows = mapper.addBlog(blog);
            if(affectedRows>0){
                System.out.println("插入数据成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }
}
