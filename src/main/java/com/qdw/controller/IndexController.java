package com.qdw.controller;

import com.github.pagehelper.PageInfo;
import com.qdw.mapper.BlogTagMapper;
import com.qdw.pojo.Blog;
import com.qdw.pojo.CommonResult;
import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import com.qdw.service.BlogService;
import com.qdw.service.SearchFromESService;
import com.qdw.service.TagService;
import com.qdw.service.TypeService;
import com.qdw.vo.BlogForSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SearchFromESService searchFromESService;

    private final int PAGE_SIZE = 6;



    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1") int page, Model model){
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page, PAGE_SIZE);
        for (Blog blog : pageInfo.getList()) {
            String s = redisTemplate.opsForValue().get("blog:"+blog.getId()+":views");
            if (s != null){
                blog.setViews(Integer.parseInt(s));
            }

        }
//        typeService.getTypeByPageHelper(1,3);
        List<Type> types = typeService.getTypesOrderByBlogNums(3);
        List<Tag> tags = tagService.getTagsOrderByBlogNums(3);
        List<Blog> blogs = blogService.getRecommendBlogTop(3);
        for (Blog blog : pageInfo.getList()) {
            System.out.println(blog.getUpdateTime());
        }
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("blogs",blogs);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    @GetMapping("/index2")
    public String indexNew(@RequestParam(name = "page",defaultValue = "1") int page, Model model) throws IOException {

        return "index2";
    }
    @GetMapping("/index2/get")
    @ResponseBody
    public Map<String,CommonResult> index2(@RequestParam(name = "page",defaultValue = "1") int page) throws IOException {

//        return  searchFromESService.searchPageMatch("Redis", 1, 3);

        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page, PAGE_SIZE);
        for (Blog blog : pageInfo.getList()) {
            String s = redisTemplate.opsForValue().get("blog:"+blog.getId()+":views");
            if (s != null){
                blog.setViews(Integer.parseInt(s));
            }

        }
//        typeService.getTypeByPageHelper(1,3);
        List<Type> types = typeService.getTypesOrderByBlogNums(3);
        List<Tag> tags = tagService.getTagsOrderByBlogNums(3);
        List<Blog> blogs = blogService.getRecommendBlogTop(3);
        for (Blog blog : pageInfo.getList()) {
            System.out.println(blog.getUpdateTime());
        }
        Map<String,CommonResult> res = new HashMap<>();

        res.put("blogs",new CommonResult<List<Blog>>(200,"成功",blogs));
        res.put("tags",new CommonResult<List<Tag>>(200,"成功",tags));
        res.put("types",new CommonResult<List<Type>>(200,"成功",types));
        return res;
    }
    @GetMapping("/put")
    @ResponseBody
    public String putDatatoEs() throws IOException {
        List<Blog> blogList = blogService.getBlogList();
        List<BlogForSearch> list = new ArrayList<>();
        for (Blog blog : blogList) {
//            list.add(new BlogForSearch(blog.getId(),blog.getTitle(),blog.getSummary()));
        }
        searchFromESService.putContent(list,"myblogs");
        return "yes";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "page",defaultValue = "1") int page,@RequestParam String query, Model model){
//        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page, PAGE_SIZE);
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelperBySearch(page, PAGE_SIZE, query);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }
    @PostMapping("/searchEs")
    public String searchEs(@RequestParam(name = "page",defaultValue = "1") int page,@RequestParam String query, Model model) throws IOException {
//        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page, PAGE_SIZE);
        List<Map<String, Object>> list = searchFromESService.searchPageMatch(query, 1, 3);
        System.out.println("!!");
        List<Integer> ids = new ArrayList<>();
        for (Map<String, Object> map : list) {
            ids.add((Integer) map.get("blogId"));
            System.out.println(map.get("blogId"));
        }
        PageInfo<Blog> pageInfo = blogService.getBlogByIds(ids,page,PAGE_SIZE);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/es/{keywords}/{page}/{size}")
    @ResponseBody
    public List<Map<String,Object>> esTest(@PathVariable String keywords,@PathVariable Integer page,@PathVariable Integer size) throws IOException {
        List<Map<String, Object>> list = searchFromESService.searchPageMatch(keywords, page, size);
        for (Map<String, Object> stringObjectMap : list) {
            for (String s : stringObjectMap.keySet()) {
                System.out.println(s+":"+stringObjectMap.get(s));
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        return searchFromESService.searchPageMatch(keywords,page,size);
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable int id,Model model){
//        Blog blog = blogService.getBlogById(id);
        Blog blog = blogService.getBlogAndConvert(id);
//        修改观看量
//        blogService.
        Long increment = redisTemplate.opsForValue().increment("blog:"+String.valueOf(blog.getId()+":views"));
        System.out.println(increment);
        model.addAttribute("blog",blog);
        List<Tag> tags = blogTagMapper.qureyTagsByBlogId(id);
        model.addAttribute("tags",tags);
        return "blog";
    }
}
