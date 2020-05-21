package com.qdw.service;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Type;

import java.util.List;
import java.util.Map;

public interface TypeService {
    List<Type> getTypeList();
    Type getTypeById(int id);
    int countBlog(int id);
    int deleteType(int id);
    int saveType(Type type);
    Type getTypeByName(String name);
    int updateType(Type type);
    //实现分页
    Pager<Type> getTypeByPage(int page, int size);
    //分页插件实现分类
    PageInfo<Type> getTypeByPageHelper(int page,int size);
    List<Type> getTypesOrderByBlogNums(int size);
//    List<Type> getTypeListAndCount();
}
