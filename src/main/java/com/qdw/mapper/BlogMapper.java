package com.qdw.mapper;

import com.qdw.pojo.Blog;
import com.qdw.pojo.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper {
    List<Blog> qureyBlogList(Map map);
    List<Blog> qureyBlogListSimple();
//    List<Blog> queryBlogByIds(List<Integer> ids);
    Blog qureyBlogById(int id);
    int countBlog();
//    List<Blog> qureyBlogPage(Map map);
    int updataBlog(Blog blog);
    Blog qureyBlogByTitle(String title);
    int insertBlog(Blog blog);
    List<Blog> qureyBlogIf(Map map);
    int deleteBlog(int id);
    List<Blog> queryBlogByTypeId(Integer typeId);
    



//    int countBlogIf(Map map);

}
