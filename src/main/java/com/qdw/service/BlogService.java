package com.qdw.service;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Blog;
import com.qdw.pojo.Pager;
import org.omg.CORBA.INTERNAL;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<Blog> getBlogList();
//    List<Blog> getBlogListSimple();
    Blog getBlogById(Integer id);
    Blog getBlogByTitle(String title);
    List<Blog> getBlogIf(Map map);
    PageInfo<Blog> getBlogByIds(List<Integer> ids,int page,int size);
    Integer countBlogs();
//    Pager<Blog> getBlogPager(int page,int size,Map map);
    int addBlog(Blog blog);
    int editBlog(Blog blog);
//    PageInfo<Blog> getBlogPageHelperIf(Integer page, Integer size, Map map);
    PageInfo<Blog> getBlogPageHelper(Integer page, Integer size);
    PageInfo<Blog> getBlogPageHelper(Integer page, Integer size,Map map);
    PageInfo<Blog> getBlogPageHelperByType(Integer page, Integer size,Integer typeId);
    PageInfo<Blog> getBlogPageHelperByTag(Integer page, Integer size,Integer tagId);
    PageInfo<Blog> getBlogPageHelperBySearch(Integer page,Integer size,String query);
    int deleteBlog(int id);

    List<Blog> getRecommendBlogTop(Integer size);
    //获取博客并将内容转换为html
    Blog getBlogAndConvert(Integer id);

    Map<String,List<Blog>> archiveBlog();
}
