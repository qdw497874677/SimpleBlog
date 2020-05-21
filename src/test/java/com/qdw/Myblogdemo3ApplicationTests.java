package com.qdw;

import com.github.pagehelper.PageInfo;
import com.qdw.mapper.*;
import com.qdw.pojo.*;
import com.qdw.service.BlogService;
import com.qdw.service.SearchFromESService;
import com.qdw.service.TagService;
import com.qdw.service.TypeService;
import com.qdw.vo.BlogForSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

@SpringBootTest
class Myblogdemo3ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SearchFromESService searchFromESService;

    @Test
    void contextLoads() {
        UserVo qdw = userMapper.selectUserByUsername("qdw");
        System.out.println(qdw);
        List<Role> roles = userMapper.selectUserAuthorities(qdw.getId());
        System.out.println(roles);
        Role role = userMapper.selectRoleById(1);
        System.out.println(role);
    }
    @Test
    void test1() {
        int count = typeMapper.count();
        System.out.println(count);
    }

    @Test
    void test2() {
        Type type = new Type();
        type.setName("lalala");
        int i = typeMapper.insertType(type);
        System.out.println(i);
    }

//    @Test
//    void test3() {
//        Pager<Blog> blogPager = blogService.getBlogPager(1, 2);
//        for (Blog row : blogPager.getRows()) {
//            System.out.println(row);
//        }
//        Map map = new HashMap();
//        map.put("page",0);
//        map.put("size",2);
//        List<Blog> blogs = blogMapper.qureyBlogPage(map);
//        for (Blog blog : blogs) {
//            System.out.println(blog);
//        }
//    }
    @Test
    void test4() {
        Map map = new HashMap();
        map.put("page",0);
        map.put("size",3);
        map.put("title",null);
        map.put("typeId",null);
        map.put("recommend",true);
        List<Blog> blogs = blogMapper.qureyBlogIf(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

    }
    @Test
    void test5() {
        Map<String,String> map = new HashMap<>();
        map.put("title",null);
        map.put("recommend",null);
        map.put("typeId",null);
        blogMapper.qureyBlogIf(map);
        PageInfo<Blog> blogPageHelper = blogService.getBlogPageHelper(1, 1, map);
        List<Blog> list = blogPageHelper.getList();
        for (Blog blog : list) {
            System.out.println(blog.getId());
        }
    }
    @Test
    void test6() {
        List<Tag> tags = tagMapper.qureyTagListByBlogId(0);
        for (Tag tag : tags) {
            System.out.println(tag.getName());
        }
        int i = blogTagMapper.hasBLogTag(0, 1);
        System.out.println("i:"+i);
    }
    @Test
    void test7() {
//        List<Tag> tags = tagMapper.qureyTagListByBlogId(1007);
//        for (Tag tag : tags) {
//            System.out.println(tag.getId());
//        }

//        List<Tag> tags1 = blogTagMapper.qureyTagsByBlogId(1007);
//        for (Tag tag : tags1) {
//            System.out.println(tag.getId());
//        }
//
//        int i = blogTagMapper.countByBlogId(1017);
//        System.out.println(i);

//        Blog blogById = blogService.getBlogById(1017);
//        List<Tag> tags = blogById.getTags();
//        for (Tag tag : tags) {
//            System.out.println(tag.getId()+" "+tag.getName());
//        }

        List<Type> types = typeMapper.qureyTypesWithBlog();

        for (Type type : types) {
            System.out.println(type.getBlogs().size());
        }
        System.out.println();
        List<Type> typesOrderByBlogNums = typeService.getTypesOrderByBlogNums(3);
        for (Type typesOrderByBlogNum : typesOrderByBlogNums) {
            System.out.println(typesOrderByBlogNum.getBlogs().size());
        }
    }
    @Test
    void test8() {
        List<Tag> tags = tagMapper.qureyTagsWithBlog();
        for (Tag tag : tags) {
            System.out.println(tag.getBlogs().size());
        }

        System.out.println();
        List<Tag> tagsOrderByBlogNums = tagService.getTagsOrderByBlogNums(3);
        for (Tag tagsOrderByBlogNum : tagsOrderByBlogNums) {
            System.out.println(tagsOrderByBlogNum.getBlogs().size());
        }

    }
    @Test
    void test9() {
        List<Blog> blogList = blogService.getBlogList();
        for (Blog blog : blogList) {
//            System.out.println(blog.getType());
            System.out.println(blog);
        }
    }

    @Test
    void test10() {
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelperBySearch(1, 5, "博客项目");
        for (Blog blog : pageInfo.getList()) {
            System.out.println(blog.getTitle());
        }
    }
    @Test
    void test11() {
        List<Tag> tags = blogTagMapper.qureyTagsByBlogId(1002);
        for (Tag tag : tags) {
            System.out.println(tag.getName());
        }
    }
    @Test
    void test12() {
        PageInfo<Blog> pageHelper = blogService.getBlogPageHelper(1, 3);
        for (Blog blog : pageHelper.getList()) {
            System.out.println(blog.getTags());
        }
    }
    @Test
    void test13() {
        Map<String,Integer> map = new HashMap<>();
        map.put("tagId",8);
        PageInfo<Blog> page = blogService.getBlogPageHelperByTag(1, 3, 8);
        for (Blog blog : page.getList()) {
            System.out.println(blog.getTitle());
        }
    }
    @Test
    void test14() {
        List<Blog> blogs = blogMapper.qureyBlogList(new HashMap());
        for (Blog blog : blogs) {
            System.out.println(blog.getTags());
        }
    }
    @Test
    void test15() {
        List<BlogTag> blogTags = new ArrayList<>();
        blogTags.add(new BlogTag(1,1019,7));
        blogTags.add(new BlogTag(1,1019,7));
        blogTags.add(new BlogTag(1,1019,7));
        blogTagMapper.insertBlogTags(blogTags);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1,"测试",null));
        tags.add(new Tag(1,"测试",null));
        tags.add(new Tag(1,"测试",null));
        tagMapper.insertTags(tags);
    }
    @Test
    void test16() {
        Map<String, List<Blog>> stringListMap = blogService.archiveBlog();
        for (String s : stringListMap.keySet()) {
            List<Blog> blogs = stringListMap.get(s);
            System.out.println(s);
            for (Blog blog : blogs) {
                System.out.println(blog.getId());
            }
            System.out.println();
        }
    }

    @Test
    void test17() {
        PageInfo<Blog> blogByIds = blogService.getBlogByIds(Arrays.asList(1019, 1020),1,3);
        for (Blog blog : blogByIds.getList()) {
            System.out.println(blog.getTitle());
        }
    }
    @Test
    void test18() throws IOException {

        List<Map<String, Object>> list = searchFromESService.searchPageMatch("Redis", 1, 3);
        for (Map<String, Object> stringObjectMap : list) {
            for (String s : stringObjectMap.keySet()) {
                System.out.println(s+":"+stringObjectMap.get(s));
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
    @Test
    void test19() throws IOException {
        List<BlogForSearch> list = new ArrayList<>();
        list.add(new BlogForSearch(1,"Redis,今天是个好日子","Redis很简单呢","Redis",Arrays.asList("日常","测试","Redis"),"权dw",new Date()));
        Boolean aBoolean = searchFromESService.putContent(list,"myblogs_new");
        System.out.println(aBoolean);
    }

}
