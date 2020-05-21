package com.qdw.service;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Blog;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface TagService {
    Tag getTagById(int id);
    List<Tag> getTagList();
    Pager<Tag> getTagPage(int page, int size);
    int editTag(Tag tag);
    int addTag(Tag tag);
    Tag getTagByName(String name);
    int deletTag(int id);
    List<Tag> getTagListByIds(String ids);
    PageInfo<Tag> getTagsPageHelper(Integer page, Integer size);
    List<Tag> getTagsOrderByBlogNums(int size);
    List<Tag> getTagsByBLogId(int blogId);
}
