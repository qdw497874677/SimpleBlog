package com.qdw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdw.mapper.TypeMapper;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;


    @Override
    public List<Type> getTypeList() {
        return typeMapper.qureyTypeList();
    }

    @Override
    public Type getTypeById(int id) {
        return typeMapper.qureyTypeById(id);
    }

    @Override
    public int countBlog(int id) {
        return typeMapper.countBlog(id);
    }

    @Override
    public int deleteType(int id) {
        return typeMapper.deleteType(id);
    }

    @Override
    public int saveType(Type type) {
        return typeMapper.insertType(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.qureyTypeByName(name);
    }

    @Override
    public int updateType(Type type) {
        return typeMapper.updataType(type);
    }


    @Override
    public Pager<Type> getTypeByPage(int page, int size) {
        Map<String,Integer> map = new HashMap<>();
        map.put("page",(page-1)*size);
        map.put("size",size);
        Pager<Type> pager = new Pager<>();
        List<Type> types = typeMapper.queryTypePage(map);
        pager.setRows(types);
        pager.setTotal(typeMapper.count());
        pager.setPage(page);
        pager.setSize(size);
        return pager;
    }

    @Override
    public PageInfo<Type> getTypeByPageHelper(int page, int size) {
        PageHelper.offsetPage(page,size);
        List<Type> types = typeMapper.qureyTypeList();
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        return pageInfo;
    }

    @Override
    public List<Type> getTypesOrderByBlogNums(int size) {
        List<Type> types = typeMapper.qureyTypesWithBlog();
//        System.out.println("servicef");
//        for (Type type : types) {
//            System.out.println(type.getBlogs().size());
//        }
//        System.out.println("servicef");
        types.sort((Type a,Type b)->{
            return b.getBlogs().size()-a.getBlogs().size();
        });
//        System.out.println("service");
//        for (Type type : types) {
//            System.out.println(type.getBlogs().size());
//        }
//        System.out.println("service");
        if (types.size()<=size){
            return types;
        }else {
            List<Type> res = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                res.add(types.get(i));
            }
            return res;
        }

    }


}
