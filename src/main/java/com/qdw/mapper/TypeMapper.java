package com.qdw.mapper;

import com.qdw.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TypeMapper {
    List<Type> qureyTypeList();
    Type qureyTypeById(int id);
    int countBlog(int id);

    int insertType(Type type);
    int count();
    List<Type> queryTypePage(Map page);

    int deleteType(int id);
    int updataType(Type type);

    Type qureyTypeByName(String name);

    List<Type> qureyTypesWithBlog();
//    List<Type> getTypeListAndCount();
}
