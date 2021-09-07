package com.carson.utils;

import org.junit.Test;

import java.util.UUID;

public class IDutils {
    public static String getId(){
        //创建UUID作为记录id进行返回,确保每条记录的唯一性
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //测试UUID能否正确生成
    @Test
    public void test(){
        System.out.println(IDutils.getId());
        System.out.println(IDutils.getId());
        System.out.println(IDutils.getId());
    }
}
