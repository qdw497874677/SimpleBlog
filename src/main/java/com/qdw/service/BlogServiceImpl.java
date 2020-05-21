package com.qdw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdw.exception.NotFoundException;
import com.qdw.mapper.BlogMapper;
import com.qdw.mapper.BlogTagMapper;
import com.qdw.mapper.TagMapper;
import com.qdw.mapper.TypeMapper;
import com.qdw.pojo.Blog;
import com.qdw.pojo.BlogTag;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Tag;
import com.qdw.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TypeMapper typeMapper;


    @Override
    public List<Blog> getBlogList() {
        return blogMapper.qureyBlogList(new HashMap());
    }

//    @Override
//    public List<Blog> getBlogListSimple() {
//        return blogMapper.qureyBlogListSimple();
//    }


    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = blogMapper.qureyBlogById(id);
//        blogTagMapper.
        blog.setTags(blogTagMapper.qureyTagsByBlogId(id));
        return blog;
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogMapper.qureyBlogByTitle(title);
    }

    @Override
    public List<Blog> getBlogIf(Map map) {
        return blogMapper.qureyBlogIf(map);
    }

    @Override
    public PageInfo<Blog> getBlogByIds(List<Integer> ids,int page,int size) {
        Map<String,List<Integer>> map = new HashMap<>();
        map.put("ids",ids);
//        return blogMapper.qureyBlogList(map);
        return getBlogPageHelper(page,size,map);
    }

    @Override
    public Integer countBlogs() {
        return blogMapper.countBlog();
    }


    @Override
    public int addBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //blog得到了自增的主键
        blogMapper.insertBlog(blog);
        System.out.println("blogId:"+blog.getId());
        System.out.println(blog.getTags().size());
        //如果有标签
        if (blog.getTags().size()!=0){
            List<BlogTag> blogTags = new ArrayList<>();
            for (Tag tag : blog.getTags()) {
//                //如果tag不存在，就添加tag
//                if (tagMapper.qureyTagByName(tag.getName())==null){
//                    tagMapper.insertTag(tag);
//                    blogTagMapper.insertBlogTag(blog.getId(),tag.getId());
//                    continue;
//                }
//                //如果没有这个tag和blog的对应关系，就添加对应关系
//                if (blogTagMapper.hasBLogTag(blog.getId(),tag.getId())<1){
//                    blogTagMapper.insertBlogTag(blog.getId(),tag.getId());
//                }
                blogTags.add(new BlogTag(1,blog.getId(),tag.getId()));
            }
            tagMapper.insertTags(blog.getTags());
            blogTagMapper.insertBlogTags(blogTags);
        }
        return 1;

    }

    @Override
    public int editBlog(Blog blog) {
        //如果有标签
        if (blog.getTags().size()!=0){
            int blogId = blog.getId();
            //删除这个blog和tag的所有对应
            blogTagMapper.deleteBlogTagByBlogId(blogId);
            List<BlogTag> blogTags = new ArrayList<>();
            for (Tag tag : blog.getTags()) {
                blogTags.add(new BlogTag(1,blog.getId(),tag.getId()));
            }
            tagMapper.insertTags(blog.getTags());
            blogTagMapper.insertBlogTags(blogTags);
        }
        blog.setUpdateTime(new Date());
        return blogMapper.updataBlog(blog);
    }

//    @Override
//    public PageInfo<Blog> getBlogPageHelperIf(Integer page, Integer size, Map map) {
//        PageHelper.startPage(page,size);
//        List<Blog> blogs = blogMapper.qureyBlogIf(map);
////        List<Blog> blogs = blogMapper.qureyBlogList(map);
//        PageInfo pageInfo = new PageInfo(blogs);
//        return pageInfo;
//    }

    @Override
    public PageInfo<Blog> getBlogPageHelper(Integer page, Integer size) {
        return getBlogPageHelper(page,size,new HashMap());
    }
    @Override
    public PageInfo<Blog> getBlogPageHelper(Integer page, Integer size,Map map) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("create_time desc");
        List<Blog> blogs = blogMapper.qureyBlogList(map);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }
    @Override
    public PageInfo<Blog> getBlogPageHelperByType(Integer page, Integer size,Integer typeId) {
        Map<String,Integer> map = new HashMap<>();
        map.put("typeId",typeId);
        return getBlogPageHelper(page,size,map);
    }

    @Override
    public PageInfo<Blog> getBlogPageHelperByTag(Integer page, Integer size, Integer tagId) {
        Map<String,Integer> map = new HashMap<>();
        map.put("tagId",tagId);
        return getBlogPageHelper(page,size,map);
    }

    @Override
    public PageInfo<Blog> getBlogPageHelperBySearch(Integer page, Integer size,String query) {
        PageHelper.startPage(page,size);
        Map map = new HashMap();
        map.put("query",query);
        List<Blog> blogs = blogMapper.qureyBlogList(map);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }

    @Override
    public int deleteBlog(int id) {
        blogTagMapper.deleteBlogTagByBlogId(id);
        return blogMapper.deleteBlog(id);
    }

    @Override
    public List<Blog> getRecommendBlogTop(Integer size) {
        Map<String,String> map = new HashMap<>();
        map.put("reconmend","1");
        PageInfo<Blog> blogPageHelper = getBlogPageHelper(1, size, map);
        return blogPageHelper.getList();
    }

    //获取博客并将内容转换为html
    @Override
    public Blog getBlogAndConvert(Integer id) {
        Blog blog = getBlogById(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog temp = new Blog();
        //把blog的值赋给新的Blog对象
        BeanUtils.copyProperties(blog,temp);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String,String> temp = new HashMap<>();
        temp.put("order","YES");
        List<Blog> blogList = blogMapper.qureyBlogList(temp);
        Calendar calendar = Calendar.getInstance();
        Map<String,List<Blog>> map = new HashMap<>();
        for (Blog blog : blogList) {
            if (blog.getCreateTime() == null){
                System.out.println("!!!"+blog.getId());
                continue;
            }
            calendar.setTime(blog.getCreateTime());
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            List<Blog> blogs = map.get(year);
            if (blogs==null){
                blogs = new ArrayList<>();
                map.put(year,blogs);
            }
            blogs.add(blog);
        }
        return map;
    }

}
