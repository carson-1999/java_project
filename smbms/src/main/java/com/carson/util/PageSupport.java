package com.carson.util;

//将分页相关代码封装成工具类
public class PageSupport {
    //当前页码来自于用户的输入
    private int currentPageNo = 1;
    //数据表中记录的总数量
    private int totalCount = 0;
    //一个页面容纳的记录数
    private int pageSize = 0;
    //展示的总页数
    private int totalPageCount = 1;

    //面向对象的封装:(属性私有,get/set,set常设置判断条件)
    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        //用户输入的页码数 需>0
        if(currentPageNo>0){
            this.currentPageNo = currentPageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount>0){
            this.totalCount = totalCount;
            //设置总页数
            this.setTotalPageCountByRs();
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize>0){
            this.pageSize = pageSize;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    /*public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }*/

    public void setTotalPageCountByRs(){
        //计算各种情况下的总页数
        if(this.totalCount%this.pageSize==0){
            this.totalPageCount = this.totalCount/this.pageSize;
        }else if(this.totalCount%this.pageSize>0){
            this.totalPageCount = this.totalCount/this.pageSize+1;
        }else{
            this.totalCount = 0;
        }
    }
}
