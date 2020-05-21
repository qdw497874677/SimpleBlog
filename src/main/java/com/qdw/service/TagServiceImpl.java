package com.qdw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdw.mapper.BlogTagMapper;
import com.qdw.mapper.TagMapper;
import com.qdw.pojo.Blog;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Override
    public Tag getTagById(int id) {
        return tagMapper.qureyTagById(id);
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.qureyTagList();
    }

    @Override
    public Pager<Tag> getTagPage(int page,int size) {
        Pager<Tag> pager = new Pager<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("page",(page-1)*size);
        map.put("size",size);
        pager.setPage(page);
        pager.setSize(size);
        pager.setRows(tagMapper.qureyTagPage(map));
        pager.setTotal(tagMapper.countTag());
        return pager;
    }

    @Override
    public int editTag(Tag tag) {
        return tagMapper.updataTag(tag);
    }

    @Override
    public int addTag(Tag tag) {
        return tagMapper.insertTag(tag);
    }


    @Override
    public Tag getTagByName(String name) {
        return tagMapper.qureyTagByName(name);
    }

    @Override
    public int deletTag(int id) {
        blogTagMapper.deleteBlogTagByTagId(id);
        return tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getTagListByIds(String ids) {
        String[] idString = ids.split(",");
        List<String> idsList = Arrays.asList(idString);
        for (String s : idsList) {
            System.out.println(s);
        }
        Map map = new HashMap();
        map.put("list",idsList);
        return tagMapper.qureyTagListByIds(map);
    }

    @Override
    public PageInfo<Tag> getTagsPageHelper(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Tag> tags = tagMapper.qureyTagList();
        PageInfo pageInfo = new PageInfo(tags);
        return pageInfo;
    }

    @Override
    public List<Tag> getTagsOrderByBlogNums(int size) {
        List<Tag> tags = tagMapper.qureyTagsWithBlog();
//        System.out.println("servicef");
//        for (Type type : types) {
//            System.out.println(type.getBlogs().size());
//        }
//        System.out.println("servicef");
        tags.sort((Tag a,Tag b)->{
            return b.getBlogs().size()-a.getBlogs().size();
        });
//        System.out.println("service");
//        for (Type type : types) {
//            System.out.println(type.getBlogs().size());
//        }
//        System.out.println("service");
        if (tags.size()<=size){
            return tags;
        }else {
            List<Tag> res = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                res.add(tags.get(i));
            }
            return res;
        }
    }

    @Override
    public List<Tag> getTagsByBLogId(int blogId) {
        blogTagMapper.qureyTagsByBlogId(blogId);
        return null;
    }

}
