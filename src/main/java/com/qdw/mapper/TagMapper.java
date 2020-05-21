package com.qdw.mapper;

import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TagMapper {
    Tag qureyTagById(int id);
    List<Tag> qureyTagList();
    List<Tag> qureyTagPage(Map map);
    int countTag();
    int updataTag(Tag tag);
    int insertTag(Tag tag);
    int insertTags(List<Tag> list);
    Tag qureyTagByName(String name);
    int deleteTag(int id);
    List<Tag> qureyTagListByIds(Map map);
    List<Tag> qureyTagListByBlogId(int blogId);
    //待修改
    List<Tag> qureyTagsWithBlog();

}
